package com.cta.guildmembersheromanager.web.controller;

import com.cta.guildmembersheromanager.dao.HeroDefinitionDAO;
import com.cta.guildmembersheromanager.model.HeroDefinition;
import com.cta.guildmembersheromanager.web.exceptions.HeroDefinitionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class HeroDefinitionController {

    @Autowired
    private HeroDefinitionDAO heroDefinitionDAO;

    @CrossOrigin
    @GetMapping(value="/Configuration/Heros")
    public List<HeroDefinition> listHeroDefinitions() {
     return heroDefinitionDAO.findAll();
    }

    @CrossOrigin
    @GetMapping(value="/Configuration/Heros/{id}")
    public HeroDefinition getHeroDefinition(@PathVariable int id) {
        HeroDefinition heroDefinition = heroDefinitionDAO.findById(id);

        if(heroDefinition==null) throw new HeroDefinitionNotFoundException("Le héros avec l'id " + id + " n'est pas configuré.");

        return heroDefinition;
    }

    @CrossOrigin
    @PostMapping(value="/Configuration/Heros")
    public ResponseEntity<Void> addHeroDefinition(@RequestBody HeroDefinition newHeroDefinition) {
        HeroDefinition heroDefinitionAdded = heroDefinitionDAO.save(newHeroDefinition);

        if (heroDefinitionAdded == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(heroDefinitionAdded.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @DeleteMapping(value="/Configuration/Heros/{id}")
    public void deleteHeroDefinition(@PathVariable int id) {
        HeroDefinition heroDefinitionToDelete = heroDefinitionDAO.findById(id);
        heroDefinitionDAO.delete(heroDefinitionToDelete);
    }

    @CrossOrigin
    @PutMapping(value="/Configuration/Heros")
    public void updateHeroDefinition(@RequestBody HeroDefinition updatedHeroDefinition) {
        heroDefinitionDAO.save(updatedHeroDefinition);
    }
}
