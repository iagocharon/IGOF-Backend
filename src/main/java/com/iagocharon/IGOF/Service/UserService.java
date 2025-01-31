package com.iagocharon.IGOF.Service;

import com.iagocharon.IGOF.Entity.User;
import com.iagocharon.IGOF.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

  @Autowired
  UserRepository userRepository;

  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) {
        return userRepository
          .findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      }
    };
  }

  public User save(User newUser) {
    return userRepository.save(newUser);
  }

  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getById(UUID id) {
    return userRepository.findById(id);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsById(UUID id) {
    return userRepository.existsById(id);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void delete(UUID id) {
    userRepository.deleteById(id);
  }

  public void delete(User user) {
    userRepository.delete(user);
  }

  public void savePasswordResetToken(User user, String token) {
    user.setResetToken(token);
    user.setTokenExpirationDate(LocalDateTime.now().plusHours(1));
    userRepository.save(user);
  }

  public Optional<User> getByResetToken(String token) {
    return userRepository.findByResetToken(token);
  }

  public boolean existsByResetToken(String token) {
    return userRepository.existsByResetToken(token);
  }
}
