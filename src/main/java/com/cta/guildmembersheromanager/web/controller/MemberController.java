package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.MemberDAO;
import com.cta.guildmembersheromanager.dao.UserDAO;
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

@RestController
public class MemberController {


    @Autowired
    private UserDAO userDAO;

    @CrossOrigin
    @GetMapping(value="/Members")
    public List<User> listMembers() {
        return userDAO.findAll();
    }

    @CrossOrigin
    @GetMapping(value="/Members/{username}")
    public User getMember(@PathVariable(value = "username") String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return user;
    }

    @CrossOrigin
    @PostMapping(value="/Members")
    public ResponseEntity<Void> addMember(@RequestBody User newUser) {
        User userAdded = userDAO.save(newUser);

        if (newUser == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(userAdded.getUsername()).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value="/Members/{username}")
    public void deleteMember(@PathVariable(value = "username") String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        userDAO.delete(user);
    }

    @CrossOrigin
    @PutMapping(value="/Members/")
    public void updateMember(@RequestBody User updatedMember) {
        userDAO.save(updatedMember);
    }
}
