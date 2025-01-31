package com.iagocharon.IGOF.Repository;

import com.iagocharon.IGOF.Entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);

  Optional<User> findById(UUID id);

  boolean existsByUsername(String username);

  boolean existsById(UUID id);

  boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);

  boolean existsByResetToken(String resetToken);

  Optional<User> findByResetToken(String resetToken);
}
