package pps.gestorClub_api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pps.gestorClub_api.dtos.members.PostMemberDto;
import pps.gestorClub_api.enums.MemberStatus;
import pps.gestorClub_api.models.Member;
import pps.gestorClub_api.services.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAll();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getById(id);
        return ResponseEntity.ok(member);
    }

    @PostMapping("")
    public ResponseEntity<Member> createMember(@Valid @RequestBody PostMemberDto member) {
        Member response = memberService.create(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable("id") Long id,
            @Valid @RequestBody PostMemberDto member) {
        Member response = memberService.update(id, member);

        return ResponseEntity.ok(response);
    }

    @PutMapping("approve/{id}")
    public ResponseEntity<String> approveMember(@PathVariable("id") Long id) {

        memberService.approveMember(id);

        return ResponseEntity.ok("Socio aprobado con éxito");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectMember(@PathVariable("id") Long id) {

        memberService.rejectMember(id);

        return ResponseEntity.ok("Socio rechazado con éxito");
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Member>> getByStatus(@PathVariable("status") MemberStatus status) {

        List<Member> response = memberService.getByStatus(status);

        return ResponseEntity.ok(response);
    }
}
