package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.data.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(@NotBlank String email);

    Optional<Users> findByResetToken(@NotBlank String resetToken);

    Optional<Users> findByUsername(@NotBlank String username);

    Optional<Users> findByEmailOrUsername(@NotBlank String username, @NotBlank String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
