package com.cta.guildmembersheromanager.dao;

import com.cta.guildmembersheromanager.model.Role;
import com.cta.guildmembersheromanager.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}