package com.cta.guildmembersheromanager.dao;

import com.cta.guildmembersheromanager.model.Hero;
import com.cta.guildmembersheromanager.model.Member;
import com.cta.guildmembersheromanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroDAO extends JpaRepository<Hero, Integer> {

    Hero findById(int id);

    List<Hero> findByowner(User owner);
}
