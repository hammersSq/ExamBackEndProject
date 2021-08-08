package com.hammers.exambackendproject.services;

import com.hammers.exambackendproject.entities.Utente;
import com.hammers.exambackendproject.exception.MailUserAlreadyExistsException;
import com.hammers.exambackendproject.repositories.UtenteRepository;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AccountingService {
    @Value("${adminUsername}")
    private String username_admin;
    @Value("${adminPassword}")
    private String password_admin;
    @Value("${ruoloClient}")
    private String ruolo = "";
    @Value("${keyclockAddres}")
    private String serverUrl;
    @Value("${Realm}")
    private String realm = "";
    @Value("${clientId}")
    private String clientId;
    private String clientSecret = "";



    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Utente registraUtente(Utente utente) throws MailUserAlreadyExistsException {
        if ( utenteRepository.existsByEmail(utente.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        return utenteRepository.save(utente);
    }
    @Transactional(readOnly = true)
    public List<Utente> getAll() {
        return utenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Utente> find(String email){
        return utenteRepository.findByEmail(email);
    }

    public Utente registraUtentePassword(Utente u,String password,String username) throws MailUserAlreadyExistsException {
        String[] email={u.getEmail()};
        String[] passwords ={password};
        String nome_client = username;
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username_admin)
                .password(password_admin)
                .build();
        for (int i = 0; i < email.length; i++) {
            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(email[i]);
            user.setEnabled(true);

            user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));

            // Get realm
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersRessource = realmResource.users();

            // Create user (requires manage-users role)
            String userId;
            Response response = usersRessource.create(user);
            System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
            System.out.println(response.getLocation());
            userId = CreatedResponseUtil.getCreatedId(response);

            System.out.printf("User created with userId: %s%n", userId);

            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(true);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(passwords[i]);

            UserResource userResource = usersRessource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);
        }
        if ( utenteRepository.existsByEmail(u.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        return utenteRepository.save(u);
        }


}
