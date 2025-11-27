package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pps.gestorClub_api.dtos.members.MemberDto;
import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.dtos.members.PutMemberDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.services.EmailService;
import pps.gestorClub_api.services.MemberService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Member getById(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        return modelMapper.map(memberEntity, Member.class);
    }

    @Override
    public List<Member> getAll() {
        List<MemberEntity> memberEntities = memberRepository.findAll();

        return memberEntities.stream()
                .map(memberEntity -> {
                    Member member = modelMapper.map(memberEntity, Member.class);
                    return member;
                }).collect(Collectors.toList());
    }

    @Override
    public Member create(PostMemberDto member) {

        if(getEmailExists(member.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        if(getDniExists(member.getDni()))
            throw new IllegalArgumentException("Dni already in use");

        // TODO: ver validaciones

        MemberEntity memberEntity = modelMapper.map(member, MemberEntity.class);

        memberEntity.setStatus(MemberStatus.PENDING);

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        String fullName = member.getName() + " " + member.getLastName();

        emailService.sendPetitionEmail(member.getEmail(), fullName);

        return modelMapper.map(memberEntitySaved, Member.class);
    }

    @Override
    public Member update(Long id, PutMemberDto member) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        memberEntity.setName(member.getName());
        memberEntity.setLastName(member.getLastName());
        memberEntity.setEmail(member.getEmail());
        memberEntity.setPhone(member.getPhone());
        memberEntity.setAddress(member.getAddress());
        memberEntity.setBirthdate(member.getBirthdate());
        memberEntity.setType(member.getType());

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        return modelMapper.map(memberEntitySaved, Member.class);
    }

    @Override
    public void deactivate(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        memberEntity.setStatus(MemberStatus.INACTIVE);
        memberRepository.save(memberEntity);
    }

    @Override
    public void activate(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        memberEntity.setStatus(MemberStatus.ACTIVE);
        memberRepository.save(memberEntity);
    }

    @Override
    public Boolean getEmailExists(String email) {

        Optional<MemberEntity> memberWithEmail = memberRepository.findByEmail(email);

        return memberWithEmail.isPresent();
    }

    @Override
    public Boolean getDniExists(String dni) {

        Optional<MemberEntity> memberWithDni = memberRepository.findByDni(dni);

        return memberWithDni.isPresent();
    }

    @Override
    public void approveMember(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        if(memberEntity.getStatus().equals(MemberStatus.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El socio ya está activo.");
        }
        memberEntity.setStatus(MemberStatus.ACTIVE);
        memberEntity.setApprovedAt(LocalDateTime.now());
        memberRepository.save(memberEntity);

        String fullName = memberEntity.getName() + " " + memberEntity.getLastName();
        emailService.sendWelcomeEmail(memberEntity.getEmail(), fullName);
    }

    @Override
    public void rejectMember(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        if(memberEntity.getStatus().equals(MemberStatus.REJECTED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El socio ya se encuentra rechazado.");
        }
        memberEntity.setStatus(MemberStatus.REJECTED);
        memberRepository.save(memberEntity);

        String fullName = memberEntity.getName() + " " + memberEntity.getLastName();
        emailService.sendRejectionEmail(memberEntity.getEmail(), fullName);
    }

    @Override
    public List<Member> getByStatus(MemberStatus status) {
        List<MemberEntity> memberEntities = memberRepository.findByStatus(status);

        return memberEntities.stream()
                .map(memberEntity -> {
                    Member member = modelMapper.map(memberEntity, Member.class);
                    return member;
                }).collect(Collectors.toList());
    }

    @Override
    public Page<MemberDto> getMembers(Pageable pageable, String search, String status, Boolean isActive) {
        Page<MemberEntity> membersPage;

        // Construir especificación para filtros dinámicos
        Specification<MemberEntity> spec = Specification.where(null);

        // Filtro por búsqueda general (nombre, apellido, DNI)
        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("dni")), "%" + search.toLowerCase() + "%")
                    )
            );
        }

        // Filtro por status
        if (status != null && !status.trim().isEmpty()) {
            try {
                MemberStatus memberStatus = MemberStatus.valueOf(status.toUpperCase());
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("status"), memberStatus)
                );
            } catch (IllegalArgumentException e) {
                // Status inválido, ignorar filtro
            }
        }

        // Filtro por activo/inactivo
        if (isActive != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("isActive"), isActive)
            );
        }

        membersPage = memberRepository.findAll(spec, pageable);
        return membersPage.map(this::convertToDto);
    }

    private MemberDto convertToDto(MemberEntity member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .lastName(member.getLastName())
                .dni(member.getDni())
                .email(member.getEmail())
                .birthdate(member.getBirthdate())
                .phone(member.getPhone())
                .address(member.getAddress())
                .type(member.getType())
                .status(member.getStatus())
                .build();
    }
}
