package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.data.models.RoleNames;
import com.boitdroid.birdapp.data.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(RoleNames roleName);
}
