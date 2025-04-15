package br.com.locahouse.security.authentication;

import br.com.locahouse.exception.handler.dto.ExceptionDto;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.repository.ImagemDoImovelRepository;
import br.com.locahouse.repository.ImovelRepository;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.security.config.SecurityConfiguration;
import br.com.locahouse.security.userdetails.UserDetailsImpl;
import br.com.locahouse.service.TokenService;
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

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    private final ImovelRepository imovelRepository;

    private final ComodoDoImovelRepository comodoDoImovelRepository;

    private final ImagemDoImovelRepository imagemDoImovelRepository;

    private final Gson gson;

    private static final String[] MENSAGENS_ERRO = {
            "Token inválido.",
            "Acesso ao recurso negado.",
            "Recurso não encontrado."
    };

    @Autowired
    private UserAuthenticationFilter(TokenService tokenService, UsuarioRepository usuarioRepository, ImovelRepository imovelRepository, ComodoDoImovelRepository comodoDoImovelRepository, ImagemDoImovelRepository imagemDoImovelRepository, Gson gson) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.imovelRepository = imovelRepository;
        this.comodoDoImovelRepository = comodoDoImovelRepository;
        this.imagemDoImovelRepository = imagemDoImovelRepository;
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
        if (this.verificarExistenciaEndpoint(requestUri, SecurityConfiguration.ENDPOINTS_COM_AUTENTICACAO)) {
            String token = this.recuperarToken(request);
            if (token == null) {
                this.gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                return;
            }

            try {
                Usuario usuario = usuarioRepository.findById(Integer.parseInt(tokenService.buscarSubject(token))).orElse(null);
                if (usuario == null) {
                    this.gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                    return;
                }

                try {
                    if (!usuario.getId().equals(this.obterIdUsuario(requestUri))) {
                        this.gerarErro(response, HttpStatus.FORBIDDEN, MENSAGENS_ERRO[1]);
                        return;
                    }
                } catch (NumberFormatException e) {
                    this.gerarErro(response, HttpStatus.NOT_FOUND, MENSAGENS_ERRO[2]);
                    return;
                }

                UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                this.gerarErro(response, HttpStatus.UNAUTHORIZED, MENSAGENS_ERRO[0]);
                return;
            }
        } else if (!this.verificarExistenciaEndpoint(requestUri, SecurityConfiguration.ENDPOINTS_SEM_AUTENTICACAO)) {
            this.gerarErro(response, HttpStatus.NOT_FOUND, MENSAGENS_ERRO[2]);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean verificarExistenciaEndpoint(String endpoint, String[] endpoints) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(endpoints).anyMatch(pattern -> pathMatcher.match(pattern, endpoint));
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader != null ? authorizationHeader.replace("Bearer ", "") : null;
    }

    private Integer obterIdUsuario(String requestUri) throws NumberFormatException {
        String[] partesUri = requestUri.split("/");
        String recursoUri = this.obterElementoDaUri(partesUri, 3);
        String operacaoUri = this.obterElementoDaUri(partesUri, 2);
        Integer idUri = Integer.parseInt(this.obterElementoDaUri(partesUri, 1));

        if (this.isImovelId(recursoUri, operacaoUri))
            return this.imovelRepository.findById(idUri).map(imovel -> imovel.getUsuario().getId()).orElse(null);
        else if (this.isComodoDoImovelId(recursoUri))
            return this.comodoDoImovelRepository.findById(idUri).map(comodo -> comodo.getImovel().getUsuario().getId()).orElse(null);
        else if (this.isImagemDoImovelId(recursoUri))
            return this.imagemDoImovelRepository.findById(idUri).map(imagem -> imagem.getImovel().getUsuario().getId()).orElse(null);

        return idUri;
    }

    private String obterElementoDaUri(String[] partesUri, int indiceReverso) {
        return partesUri[partesUri.length - indiceReverso];
    }

    private boolean isImovelId(String recursoUri, String operacaoUri) {
        return recursoUri.equals("imoveis") && !operacaoUri.equals("cadastrar") && !operacaoUri.equals("buscar-meus") || recursoUri.equals("comodos-imoveis") && operacaoUri.equals("cadastrar") || recursoUri.equals("imagens-imoveis") && operacaoUri.equals("upload");
    }

    private boolean isComodoDoImovelId(String recursoUri) {
        return recursoUri.equals("comodos-imoveis");
    }

    private boolean isImagemDoImovelId(String recursoUri) {
        return recursoUri.equals("imagens-imoveis");
    }

    private void gerarErro(HttpServletResponse response, HttpStatus httpStatus, String mensagem) throws IOException {
        response.setStatus(httpStatus.value());
        response.getWriter().write(gson.toJson(new ExceptionDto(httpStatus, List.of(mensagem))));
    }
}
