package br.com.locahouse.security.authentication;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.security.config.SecurityConfiguration;
import br.com.locahouse.security.userdetails.UserDetailsImpl;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private UserAuthenticationFilter(JwtTokenService jwtTokenService, UsuarioRepository usuarioRepository) {
        this.jwtTokenService = jwtTokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String requestUri = request.getRequestURI();
        if (verificarEndpointComAutenticacao(requestUri)) {
            String token = recuperarToken(request);
            if (token != null) {
                try {
                    Usuario usuario = usuarioRepository.findByEmail(jwtTokenService.recuperarSubject(token)).get();
                    if (!usuario.getId().equals(extrairIdUsuarioDaUri(requestUri))) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.getWriter().write("Acesso ao recurso negado.");
                        return;
                    }

                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

                    // Cria um objeto de autenticação do Spring Security
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                    // Define o objeto de autenticação no contexto de segurança do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTVerificationException e) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Token inválido.");
                    return;
                }
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("O token está ausente.");
                return;
            }
        } else if (!verificarEndpointSemAutenticacao(requestUri)) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("Recurso não encontrado.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean verificarEndpointComAutenticacao(String requestUri) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(SecurityConfiguration.ENDPOINTS_COM_AUTENTICACAO).anyMatch(
                pattern -> pathMatcher.match(pattern, requestUri)
        );
    }

    private boolean verificarEndpointSemAutenticacao(String requestUri) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(SecurityConfiguration.ENDPOINTS_SEM_AUTENTICACAO).anyMatch(
                pattern -> pathMatcher.match(pattern, requestUri)
        );
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    /**
     * Retorna a última parte da URI, referente ao ID do usuário.
     */
    public Integer extrairIdUsuarioDaUri(String requestUri) {
        String[] partesUri = requestUri.split("/");
        return Integer.parseInt(partesUri[partesUri.length - 1]); //
    }
}
