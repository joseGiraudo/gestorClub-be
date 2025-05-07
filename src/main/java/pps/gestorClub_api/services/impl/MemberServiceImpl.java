package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.repositories.MemberRepository;
import pps.gestorClub_api.services.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

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

        // TODO: Faltan validaciones de dni y alguna mas

        MemberEntity memberEntity = modelMapper.map(member, MemberEntity.class);

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        return modelMapper.map(memberEntitySaved, Member.class);
    }

    @Override
    public Member update(Long id, PostMemberDto member) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        memberEntity.setName(member.getName());
        memberEntity.setLastName(member.getLastName());
        memberEntity.setEmail(member.getEmail());
        memberEntity.setBirthdate(member.getBirthdate());
        memberEntity.setType(member.getType());

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        return modelMapper.map(memberEntitySaved, Member.class);
    }

    @Override
    public void delete(Long id) {

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        memberEntity.setIsActive(false);
        memberRepository.save(memberEntity);
    }

    @Override
    public Boolean getEmailExists(String email) {

        Optional<MemberEntity> memberWithEmail = memberRepository.findByEmail(email);

        return memberWithEmail.isPresent();
    }
}
