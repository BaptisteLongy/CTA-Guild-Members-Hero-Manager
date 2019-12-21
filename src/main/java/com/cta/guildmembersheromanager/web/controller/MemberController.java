package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.MemberDAO;
import com.cta.guildmembersheromanager.model.Member;
import com.cta.guildmembersheromanager.web.exceptions.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class MemberController {


    @Autowired
    private MemberDAO memberDAO;

    @CrossOrigin
    @GetMapping(value="/Members")
    public List<Member> listMembers() {
        return memberDAO.findAll();
    }

    @CrossOrigin
    @GetMapping(value="/Members/{id}")
    public Member getMember(@PathVariable int id) {
        Member member = memberDAO.findById(id);

        if(member==null) throw new MemberNotFoundException("Le membre avec l'id " + id + " n'existe pas.");

        return member;
    }

    @CrossOrigin
    @PostMapping(value="/Members")
    public ResponseEntity<Void> addMember(@RequestBody Member newMember) {
        Member memberAdded = memberDAO.save(newMember);

        if (newMember == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(memberAdded.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value="/Members/{id}")
    public void deleteMember(@PathVariable int id) {
        Member memberToDelete = memberDAO.findById(id);
        memberDAO.delete(memberToDelete);
    }

    @CrossOrigin
    @PutMapping(value="/Members/")
    public void updateMember(@RequestBody Member updatedMember) {
        memberDAO.save(updatedMember);
    }
}
