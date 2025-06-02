package com.example.libraryAuthorizationServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Конфигурация безопасности для авторизации
 *
 * @author ITWeiss
 */
@Configuration
public class SecurityConfig {

  /**
   * Создание фильтра безопасности для авторизации с помощью OAuth2 Authorization Server
   */
  @Bean
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
        new OAuth2AuthorizationServerConfigurer();

    http
        .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
        .with(authorizationServerConfigurer, Customizer.withDefaults())
        .exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

    return http.build();
  }

  /**
   * Настройка сервера авторизации
   */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .issuer("http://localhost:9000")
        .build();
  }
}
