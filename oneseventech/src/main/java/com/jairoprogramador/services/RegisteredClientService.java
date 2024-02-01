package com.jairoprogramador.services;

import com.jairoprogramador.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class RegisteredClientService implements RegisteredClientRepository {

    private ClientRepository clientRepository;

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var clientOpt = this.clientRepository.findByClientId(clientId);
        return clientOpt.map(client -> {
           var authorizationGrantTypes = Arrays.stream(client.getGrantTypes().split(","))
                   .map(AuthorizationGrantType::new)
                   .toList();
           var clientAuthorizationMethods = Arrays.stream(client.getAuthenticationMethods().split(","))
                    .map(ClientAuthenticationMethod::new)
                    .toList();
            var scopes = Arrays.stream(client.getScopes().split(","))
                    .toList();
            return RegisteredClient
                    .withId(client.getId().toString())
                    .clientId(client.getClientId())
                    .clientSecret(client.getClientSecret())
                    .clientName(client.getClientName())
                    .redirectUri(client.getRedirectUri())
                    .postLogoutRedirectUri(client.getRedirectUriLogout())
                    .clientAuthenticationMethod(clientAuthorizationMethods.get(0))
                    .clientAuthenticationMethod(clientAuthorizationMethods.get(1))
                    .scope(scopes.get(0))
                    .scope(scopes.get(1))
                    .authorizationGrantType(authorizationGrantTypes.get(0))
                    .authorizationGrantType(authorizationGrantTypes.get(1))
                    .tokenSettings(this.tokenSettings())
                    .build();

        }).orElseThrow(() -> new BadCredentialsException("Client not exist"));
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    private TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(8))
                .build();
    }

}