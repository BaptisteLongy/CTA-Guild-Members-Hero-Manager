package com.cta.guildmembersheromanager.dao;

import com.cta.guildmembersheromanager.model.HeroDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroDefinitionDAO extends JpaRepository<HeroDefinition, Integer> {

    HeroDefinition findById(int id);

}
