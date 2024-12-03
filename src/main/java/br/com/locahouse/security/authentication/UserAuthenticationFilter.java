package br.com.locahouse.security.authentication;

import br.com.locahouse.dto.erro.ErroDto;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.repository.ImovelRepository;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.security.config.SecurityConfiguration;
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

    private static final String[] MENSAGENS_ERRO = {
            "Token inválido.",
            "Acesso ao recurso negado.",
            "Recurso não encontrado."
    };

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
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String requestUri = request.getRequestURI();
        if (verificarEndpointComAutenticacao(requestUri)) {
            String token = recuperarToken(request);
            if (token == null) {
                gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                return;
            }

            try {
                Usuario usuario = usuarioRepository.findById(Integer.parseInt(jwtTokenService.recuperarSubject(token))).orElse(null);
                if (usuario == null) {
                    gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                    return;
                }

                try {
                    if (!usuario.getId().equals(obterIdUsuario(requestUri))) {
                        gerarErro(response, HttpStatus.FORBIDDEN, MENSAGENS_ERRO[1]);
                        return;
                    }
                } catch (NumberFormatException e) {
                    gerarErro(response, HttpStatus.NOT_FOUND, MENSAGENS_ERRO[2]);
                    return;
                }

                UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                return;
            }
        } else if (!verificarEndpointSemAutenticacao(requestUri)) {
            gerarErro(response, HttpStatus.NOT_FOUND, MENSAGENS_ERRO[2]);
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
        return authorizationHeader != null ? authorizationHeader.replace("Bearer ", "") : null;
    }

    private Integer obterIdUsuario(String requestUri) throws NumberFormatException {
        String[] partesUri = requestUri.split("/");
        String recursoUri = obterElementoDaUri(partesUri, 3);
        String operacaoUri = obterElementoDaUri(partesUri, 2);
        Integer idUri = Integer.parseInt(obterElementoDaUri(partesUri, 1));

        if (isImovelId(recursoUri, operacaoUri))
            return imovelRepository.findById(idUri).map(imovel -> imovel.getUsuario().getId()).orElse(null);
        else if (isComodoDoImovelId(recursoUri))
            return comodoDoImovelRepository.findById(idUri).map(comodo -> comodo.getImovel().getUsuario().getId()).orElse(null);

        return idUri;
    }

    private String obterElementoDaUri(String[] partesUri, int indiceReverso) {
        return partesUri[partesUri.length - indiceReverso];
    }

    private boolean isImovelId(String recursoUri, String operacaoUri) {
        return recursoUri.equals("imoveis") && !operacaoUri.equals("cadastrar") && !operacaoUri.equals("buscar-meus") || recursoUri.equals("comodos-imoveis") && operacaoUri.equals("cadastrar");
    }

    private boolean isComodoDoImovelId(String recursoUri) {
        return recursoUri.equals("comodos-imoveis");
    }

    private void gerarErro(HttpServletResponse response, HttpStatus httpStatus, String mensagem) throws IOException {
        response.setStatus(httpStatus.value());
        String jsonResponse = gson.toJson(new ErroDto(httpStatus, List.of(mensagem)));
        response.getWriter().write(jsonResponse);
    }
}
