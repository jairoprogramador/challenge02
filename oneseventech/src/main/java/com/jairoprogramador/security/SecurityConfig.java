package com.jairoprogramador.security;

import com.jairoprogramador.repositories.UserRepository;
import com.jairoprogramador.services.AppUserDetails;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    private static final String[] ADMIN_RESOURCES = {"/subscription/**","/payment/**"};
    private static final String ROLE_ADMIN = "ADMIN";

    private static final String LOGIN_RESOURCE = "/login";
    private static final String RSA = "RSA";
    private static final Integer RSA_SIZE = 2048;
    private static final String APPLICATION_OWNER = "jairogalvez.com";

    @Autowired
    private UserRepository userRepository;

    @Bean
    @Order(1)
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                .tokenRevocationEndpoint(tokenRevocationEndpoint -> tokenRevocationEndpoint
                        .revocationResponseHandler((request, response, authentication) -> {
                            /* delete session here... */
                            response.setStatus(HttpStatus.OK.value());
                        }));

        httpSecurity.exceptionHandling( e ->
                e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_RESOURCE)));

        httpSecurity.cors(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain userSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var ignoreUserPOST = AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/user");
        var requestHandler = new CsrfTokenRequestAttributeHandler();

        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.GET,"/user/**").hasRole(ROLE_ADMIN)
                .requestMatchers(ADMIN_RESOURCES).hasRole(ROLE_ADMIN)
                .anyRequest().permitAll())
                .csrf(csrf -> csrf
                    .csrfTokenRequestHandler(requestHandler)
                    .ignoringRequestMatchers(ignoreUserPOST)
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        httpSecurity.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        httpSecurity.cors(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(PasswordEncoder encoder, AppUserDetails userDetails){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(encoder);
        authProvider.setUserDetailsService(userDetails);
        return  authProvider;
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authConverter = new JwtGrantedAuthoritiesConverter();
        authConverter.setAuthoritiesClaimName("roles");
        authConverter.setAuthorityPrefix("");

        var converterResponse = new JwtAuthenticationConverter();
        converterResponse.setJwtGrantedAuthoritiesConverter(authConverter);
        return  converterResponse;
    }

    @Bean
    JWKSource<SecurityContext> jwtSource(){
        var rsa = generateRSA();
        var jwkSet = new JWKSet(rsa);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer(){
        return context -> {
            var authentication = context.getPrincipal();

            var userOp = this.userRepository.findByEmail(authentication.getName());
            String userId = userOp.map(user -> user.getId().toString()).orElse("");

            var authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            if(context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)){
              context.getClaims().claims(claim ->
                      claim.putAll(Map.of(
                              "roles", authorities,
                              "owner", APPLICATION_OWNER,
                              "date_request", LocalDateTime.now().toString(),
                              "userId", userId)
                      ));
          }
        };
    }

    private static KeyPair generateKeyPair(){
        KeyPair keyPair;
        try{
            var keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(RSA_SIZE);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch (NoSuchAlgorithmException e){
            throw new IllegalStateException(e);
        }
        return keyPair;
    }

    private static RSAKey generateRSA(){
        var keyPair = generateKeyPair();
        var publicKey = (RSAPublicKey)keyPair.getPublic();
        var privateKey = (RSAPrivateKey)keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

}
