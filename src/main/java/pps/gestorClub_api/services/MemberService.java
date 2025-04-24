package pps.gestorClub_api.services;

import pps.gestorClub_api.models.Member;

import java.util.List;

public interface MemberService {
    Member getById(Long id);

    List<Member> getAll();

    Member create(Member member);

    Member update(Long id, Member member);

    void delete(Long id);

    Boolean getEmailExists(String email);
}
