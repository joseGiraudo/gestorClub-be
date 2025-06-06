package pps.gestorClub_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pps.gestorClub_api.dtos.members.MemberDto;
import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.dtos.members.PutMemberDto;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.models.Member;

import java.util.List;

public interface MemberService {

    Member getById(Long id);

    List<Member> getAll();

    Member create(PostMemberDto member);

    Member update(Long id, PutMemberDto member);

    void delete(Long id);

    Boolean getEmailExists(String email);

    Boolean getDniExists(String dni);

    void approveMember(Long id);

    void rejectMember(Long id);

    List<Member> getByStatus(MemberStatus status);

    Page<MemberDto> getMembers(Pageable pageable, String search, String status, Boolean isActive);
}
