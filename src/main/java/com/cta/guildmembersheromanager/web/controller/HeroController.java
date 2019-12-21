package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.HeroDAO;
import com.cta.guildmembersheromanager.dao.MemberDAO;
import com.cta.guildmembersheromanager.model.Hero;
import com.cta.guildmembersheromanager.model.Member;
import com.cta.guildmembersheromanager.web.exceptions.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class HeroController {

    @Autowired
    private HeroDAO heroDAO;

    @Autowired
    private MemberDAO memberDAO;

    @CrossOrigin
    @GetMapping(value="/Members/{id}/Heros")
    public List<Hero> getHerosListForMember(@PathVariable int id) {
        Member member = memberDAO.findById(id);

        if(member==null) throw new MemberNotFoundException("Le membre avec l'id " + id + " n'existe pas.");

        return heroDAO.findByowner(member);
    }

    @CrossOrigin
    @PostMapping(value="/Members/{id_member}/Heros")
    public ResponseEntity<Void> addHero(@PathVariable int id_member, @RequestBody Hero newHero) {
        Member member = memberDAO.findById(id_member);

        if (member == null) throw new MemberNotFoundException("Le membre avec l'id " + id_member + " n'existe pas.");

        if (newHero == null) {
            return ResponseEntity.noContent().build();
        }
        newHero.setOwner(member);
        heroDAO.save(newHero);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newHero.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value="/Members/{id_member}/Heros")
    public void updateHero(@RequestBody Hero updatedHero) {
        heroDAO.save(updatedHero);
    }
}
