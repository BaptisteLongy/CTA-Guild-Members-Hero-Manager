package com.cta.guildmembersheromanager.dao;

import com.cta.guildmembersheromanager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAO extends JpaRepository<Member, Integer> {

    Member findById(int id);
}
