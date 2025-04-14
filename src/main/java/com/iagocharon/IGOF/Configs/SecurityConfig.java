package com.iagocharon.IGOF.Configs;

import com.iagocharon.IGOF.Jwt.JwtAuthenticationFilter;
import com.iagocharon.IGOF.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
          .addMapping("/**")
          .allowedOrigins(
            "http://localhost:4200",
            "http://localhost:4201",
            "http://localhost:4202",
            "http://localhost:4203",
            "https://pacientes.igof.com.ar",
            "https://medicos.igof.com.ar",
            "https://admin.igof.com.ar",
            "https://ecografias.igof.com.ar",
            "https://igof.com.ar",
            "https://www.pacientes.igof.com.ar",
            "https://www.medicos.igof.com.ar",
            "https://www.admin.igof.com.ar",
            "https://www.ecografias.igof.com.ar",
            "https://www.igof.com.ar"
          )
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedHeaders("*")
          .allowCredentials(false);
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration config
  ) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
      .cors(cors -> cors.disable()) // Deshabilitar CORS completamente
      .sessionManagement(
        session ->
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin
        // sesiones
      )
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers(HttpMethod.POST, "/api/auth/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/**")
          .permitAll()
          .requestMatchers(HttpMethod.DELETE, "/**")
          .permitAll()
          .requestMatchers(HttpMethod.PUT, "/**")
          .permitAll()
          .requestMatchers(HttpMethod.OPTIONS, "/**")
          .permitAll()
          .requestMatchers("/api/**")
          .authenticated()
          .anyRequest()
          .denyAll()
      )
      .authenticationProvider(authenticationProvider()) // Filtro JWT
      .addFilterBefore(
        jwtAuthenticationFilter, // Filtro JWT
        UsernamePasswordAuthenticationFilter.class
      );

    return http.build();
  }
}
