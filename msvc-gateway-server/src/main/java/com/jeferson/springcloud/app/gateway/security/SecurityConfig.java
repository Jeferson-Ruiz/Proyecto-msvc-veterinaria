package com.jeferson.springcloud.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import static org.springframework.security.config.Customizer.withDefaults;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange(authz -> {
            //rutas publicas
            authz.pathMatchers("/authorized", "/logout").permitAll()
                
            //modulo users
            .pathMatchers(HttpMethod.GET,"api/sav/users", 
                                         "api/sav/users/**")
                                        .hasAnyRole("MANAGER","ADMINISTRATIVE")
            .pathMatchers("api/sav/users/**").hasRole("MANAGER")

            //modulo workstaff
            .pathMatchers(HttpMethod.GET,"api/sav/workstaff/status/**",
                                         "api/sav/workstaff/id/**",
                                         "api/sav/workstaff/document/**",
                                         "api/sav/workstaff/all")
                                         .hasAnyRole("RRHH", "MANAGER", "ADMINISTRATIVE")
            .pathMatchers("api/sav/workstaff/**").hasAnyRole("MANAGER","RRHH")

            //Modulo MEDIACAL
            .pathMatchers(HttpMethod.GET,"api/sav/medical/pet/**",
                                         "api/sav/medical/owner",
                                         "api/sav/medical/owner/**",
                                         "api/sav/medical/consultation",
                                         "api/sav/medical/consultation/**")
                                         .hasAnyRole("USER", "MEDICAL", "ADMINISTRATIVE", "MANAGER")

            .pathMatchers(HttpMethod.GET,"api/sav/medical", 
                                         "api/sav/medical/**")
                                         .hasAnyRole("MEDICAL", "ADMINISTRATIVE")
            .pathMatchers("api/sav/medical/**").hasRole("MEDICAL")
            
                .anyExchange().authenticated();
        }).cors(csrf -> csrf.disable())
            .oauth2Login(withDefaults())
            .oauth2Client(withDefaults())
            .oauth2ResourceServer( oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<AbstractAuthenticationToken>>(){

                @Override
                public Mono<AbstractAuthenticationToken> convert(Jwt source) {
                    Collection<String> roles = source.getClaimAsStringList("roles");
                    Collection<GrantedAuthority> authorities = roles.stream()
                                                            .map(SimpleGrantedAuthority::new)
                                                            .collect(Collectors.toList());

                    return Mono.just(new JwtAuthenticationToken(source, authorities));
                }
            })))
            .build();
    }
}
