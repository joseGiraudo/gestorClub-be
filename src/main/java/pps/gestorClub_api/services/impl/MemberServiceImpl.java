package pps.gestorClub_api.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pps.gestorClub_api.entities.MemberEntity;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.models.User;
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

        if(memberEntities.isEmpty())
            throw new EntityNotFoundException("No se encontraron socios");

        return memberEntities.stream()
                .map(memberEntity -> {
                    Member member = modelMapper.map(memberEntity, Member.class);
                    return member;
                }).collect(Collectors.toList());
    }

    @Override
    public Member create(Member member) {

        if(getEmailExists(member.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        MemberEntity memberEntity = modelMapper.map(member, MemberEntity.class);

        MemberEntity memberEntitySaved = memberRepository.save(memberEntity);

        return modelMapper.map(memberEntitySaved, Member.class);
    }

    @Override
    public Member update(Long id, Member member) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el socio con id: " + id));

        MemberEntity memberEntitySaved = memberRepository.save(modelMapper.map(member, MemberEntity.class));

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
