package com.jeferson.springcloud.msvc.oauth.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import com.jeferson.springcloud.msvc.oauth.models.User;
import com.jeferson.springcloud.msvc.oauth.models.UserStatus;
import jakarta.ws.rs.NotFoundException;

@Service
public class UsersService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final WebClient.Builder client;

    public UsersService(Builder client, PasswordEncoder passwordEncoder) {
        this.client = client;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        try {
            User user = client.build().get().uri("/username/{username}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();

            List<GrantedAuthority> roles = user.getRoles()
                                                .stream()
                                                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                                .collect(Collectors.toList());
            
            boolean enabled = user.getStatus() == UserStatus.ACTIVE;

            System.out.println("Informacion que llega");
            System.out.println(username);
            System.out.println(roles);
            System.out.println(passwordEncoder);
            
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                    user.getPassword(),
                                                                    enabled,
                                                                    true,
                                                                    true,
                                                                    true,
                                                                    roles);

        } catch (WebClientRequestException e) {
            throw new NotFoundException("Error de login, username "+ username +" no existe");
        }
    }

}
