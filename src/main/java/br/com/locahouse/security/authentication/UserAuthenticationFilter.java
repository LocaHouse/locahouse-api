package br.com.locahouse.security.authentication;

import br.com.locahouse.dto.erro.ErroDto;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.repository.ImovelRepository;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.config.SecurityConfiguration;
import br.com.locahouse.security.userdetails.UserDetailsImpl;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
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
import java.util.List;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UsuarioRepository usuarioRepository;

    private final ImovelRepository imovelRepository;

    private final ComodoDoImovelRepository comodoDoImovelRepository;

    private final Gson gson;

    @Autowired
    private UserAuthenticationFilter(JwtTokenService jwtTokenService, UsuarioRepository usuarioRepository, ImovelRepository imovelRepository, ComodoDoImovelRepository comodoDoImovelRepository, Gson gson) {
        this.jwtTokenService = jwtTokenService;
        this.usuarioRepository = usuarioRepository;
        this.imovelRepository = imovelRepository;
        this.comodoDoImovelRepository = comodoDoImovelRepository;
        this.gson = gson;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String requestUri = request.getRequestURI();
        if (verificarEndpointComAutenticacao(requestUri)) {
            String token = recuperarToken(request);
            if (token != null) {
                try {
                    Usuario usuario = usuarioRepository.findById(Integer.parseInt(jwtTokenService.recuperarSubject(token))).orElse(null);
                    if (usuario == null) {
                        gerarErro(response, HttpStatus.UNAUTHORIZED, "Token inválido.");
                        return;
                    }
                    try {
                        if (!usuario.getId().equals(extrairIdUsuarioDaUri(requestUri))) {
                            gerarErro(response, HttpStatus.FORBIDDEN, "Acesso ao recurso negado.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        gerarErro(response, HttpStatus.NOT_FOUND, "Recurso não encontrado.");
                        return;
                    }
                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()); // Cria um objeto de autenticação do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authentication);                                                                             // Define o objeto de autenticação no contexto de segurança do Spring Security
                } catch (JWTVerificationException e) {
                    gerarErro(response, HttpStatus.UNAUTHORIZED, "Token inválido.");
                    return;
                }
            } else {
                gerarErro(response, HttpStatus.UNAUTHORIZED, "O token está ausente.");
                return;
            }
        } else if (!verificarEndpointSemAutenticacao(requestUri)) {
            gerarErro(response, HttpStatus.NOT_FOUND, "Recurso não encontrado.");
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

    private Integer extrairIdUsuarioDaUri(String requestUri) throws NumberFormatException {
        String[] partesUri = requestUri.split("/");
        String tipoRecurso = partesUri[partesUri.length - 3];
        String operacao = partesUri[partesUri.length - 2];
        Integer id = Integer.parseInt(partesUri[partesUri.length - 1]);
        if (tipoRecurso.equals("imoveis") && !operacao.equals("cadastrar") || tipoRecurso.equals("comodos-imoveis") && operacao.equals("cadastrar")) {
            if (this.imovelRepository.existsById(id)) {
                return this.imovelRepository.findById(id).get().getUsuario().getId();
            }
            return null;
        } else if (tipoRecurso.equals("comodos-imoveis")) {
            if (this.comodoDoImovelRepository.existsById(id)) {
                return this.comodoDoImovelRepository.findById(id).get().getImovel().getUsuario().getId();
            }
            return null;
        }
        return id;
    }

    private void gerarErro(HttpServletResponse response, HttpStatus httpStatus, String mensagem) throws IOException {
        response.setStatus(httpStatus.value());
        String jsonResponse = gson.toJson(
                new ErroDto(
                        httpStatus,
                        List.of(mensagem)
                )
        );
        response.getWriter().write(jsonResponse);
    }
}
