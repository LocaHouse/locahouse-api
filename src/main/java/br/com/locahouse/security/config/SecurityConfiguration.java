package br.com.locahouse.security.config;

import br.com.locahouse.security.authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(UserAuthenticationFilter userAuthenticationFilter) {
        this.userAuthenticationFilter = userAuthenticationFilter;
    }

    public static final String[] ENDPOINTS_SEM_AUTENTICACAO = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/usuarios/login",
            "/api/v1/usuarios/cadastrar",
            "/api/v1/imoveis/buscar/*",
            "/api/v1/imoveis/buscar-disponiveis"
    };

    public static final String[] ENDPOINTS_COM_AUTENTICACAO = {
            "/api/v1/usuarios/buscar/*",
            "/api/v1/usuarios/atualizar/*",
            "/api/v1/usuarios/deletar/*",
            "/api/v1/usuarios/atualizar-senha/*",
            "/api/v1/imoveis/cadastrar/*",
            "/api/v1/imoveis/atualizar/*",
            "/api/v1/imoveis/deletar/*",
            "/api/v1/imoveis/buscar-meus/*",
            "/api/v1/comodos-imoveis/cadastrar/*",
            "/api/v1/comodos-imoveis/buscar/*",
            "/api/v1/comodos-imoveis/atualizar/*",
            "/api/v1/comodos-imoveis/deletar/*"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Desativa a proteção contra CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Configura a política de criação de sessão como stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Habilita a autorização para as requisições HTTP
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(ENDPOINTS_SEM_AUTENTICACAO).permitAll()
                        .requestMatchers(ENDPOINTS_COM_AUTENTICACAO).authenticated()
                        .anyRequest().denyAll())
                // Filtro de autenticação personalizado
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
