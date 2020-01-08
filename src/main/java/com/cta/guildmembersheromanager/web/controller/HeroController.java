package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.HeroDAO;
import com.cta.guildmembersheromanager.dao.UserDAO;
import com.cta.guildmembersheromanager.model.Hero;
import com.cta.guildmembersheromanager.model.Member;
import com.cta.guildmembersheromanager.model.User;
import com.cta.guildmembersheromanager.web.exceptions.MemberNotFoundException;
import com.cta.guildmembersheromanager.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class HeroController {

    @Autowired
    private HeroDAO heroDAO;

    @Autowired
    private UserDAO userDAO;

    @CrossOrigin
    @GetMapping(value="/Members/{username}/Heros")
    public List<Hero> getHerosListForMember(@PathVariable(value = "username") String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return heroDAO.findByowner(user);
    }

    @CrossOrigin
    @PostMapping(value="/Members/{username}/Heros")
    public ResponseEntity<Void> addHero(@PathVariable(value = "username")String username, @RequestBody Hero newHero) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (newHero == null) {
            return ResponseEntity.noContent().build();
        }
        newHero.setOwner(user);
        heroDAO.save(newHero);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newHero.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PutMapping(value="/Members/{username}/Heros")
    public void updateHero(@RequestBody Hero updatedHero) {
        heroDAO.save(updatedHero);
    }
}
