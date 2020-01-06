package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.UserDAO;
import com.cta.guildmembersheromanager.model.User;
import com.cta.guildmembersheromanager.payload.UserIdentityAvailability;
import com.cta.guildmembersheromanager.payload.UserProfile;
import com.cta.guildmembersheromanager.payload.UserSummary;
import com.cta.guildmembersheromanager.security.CurrentUser;
import com.cta.guildmembersheromanager.security.UserPrincipal;
import com.cta.guildmembersheromanager.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDAO userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getRoles());

        return userProfile;
    }

    @GetMapping("/users/")
    public List<UserProfile> getAllUserProfiles() {
        List<UserProfile> userProfileList = new ArrayList();
        List<User> userList = userRepository.findAll();

        for (User user:userList
             ) {
            userProfileList.add(new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getRoles()));
        }

        return userProfileList;
    }

    @PostMapping("/users/{username}")
    public UserProfile updateUserProfile(@PathVariable(value = "username") String username, @Valid @RequestBody UserProfile userProfile) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (userProfile.getRoles() != null)  {
            user.setRoles(userProfile.getRoles());
        }

        userRepository.save(user);

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getRoles());
    }
}