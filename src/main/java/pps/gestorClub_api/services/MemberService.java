package pps.gestorClub_api.services;

import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.models.Member;

import java.util.List;

public interface MemberService {

    Member getById(Long id);

    List<Member> getAll();

    Member create(PostMemberDto member);

    Member update(Long id, PostMemberDto member);

    void delete(Long id);

    Boolean getEmailExists(String email);

    void approveMember(Long id);

    void rejectMember(Long id);
}
