package com.iagocharon.IGOF.Controller;

import com.iagocharon.IGOF.Dto.AuthResponse;
import com.iagocharon.IGOF.Dto.LoginRequest;
import com.iagocharon.IGOF.Dto.Message;
import com.iagocharon.IGOF.Dto.PatientSignupRequest;
import com.iagocharon.IGOF.Entity.User;
import com.iagocharon.IGOF.Service.AuthService;
import com.iagocharon.IGOF.Service.EmailService;
import com.iagocharon.IGOF.Service.UserService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  AuthService authService;

  @Autowired
  UserService userService;

  @Autowired
  EmailService emailService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping(value = "login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping(value = "signup/patient")
  public ResponseEntity<AuthResponse> signup(
    @RequestBody PatientSignupRequest request
  ) {
    return ResponseEntity.ok(authService.createPatient(request));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
    System.out.println("Received forgot password request for email: " + email);
    if (!userService.existsByEmail(email)) {
      return new ResponseEntity<>(
        new Message("User not found."),
        HttpStatus.NOT_FOUND
      );
    }

    System.out.println("Email: " + email);

    User user = userService.getByEmail(email).get();

    String token = UUID.randomUUID().toString();
    userService.savePasswordResetToken(user, token);

    String resetLink =
      "https://pacientes.igof.com.ar/#/password-form?token=" + token;

    String message =
      "Hola " +
      user.getUsername() +
      ",\n\n" +
      "Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.\n" +
      "Si no has solicitado este cambio, por favor ignora este mensaje.\n\n" +
      "Para restablecer tu contraseña, haz clic en el siguiente enlace:\n" +
      resetLink +
      "\n\n" +
      "\n\n" +
      "Gracias,\n" +
      "El equipo de IGOF";
    System.out.println("Generated reset link: " + resetLink);
    emailService.sendNewMail(
      user.getEmail(),
      "IGOF - Restablecimiento de Contraseña",
      message
    );

    System.out.println("Password reset email sent to: " + user.getEmail());

    return new ResponseEntity<>(new Message("Email sent."), HttpStatus.OK);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(
    @RequestParam("token") String token,
    @RequestParam("password") String newPassword
  ) {
    if (!userService.existsByResetToken(token)) {
      return new ResponseEntity<>(
        new Message("Invalid token."),
        HttpStatus.NOT_FOUND
      );
    }
    User user = userService.getByResetToken(token).get();

    if (user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
      return new ResponseEntity<>(
        new Message("Token expired."),
        HttpStatus.BAD_REQUEST
      );
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    user.setResetToken(null);
    user.setTokenExpirationDate(null);
    userService.save(user);

    return new ResponseEntity<>(new Message("Password reset."), HttpStatus.OK);
  }
}
