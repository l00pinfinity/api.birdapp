package com.boitdroid.birdapp.repository;
import com.boitdroid.birdapp.model.role.Role;
import com.boitdroid.birdapp.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
