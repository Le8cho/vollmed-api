package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Obtenemos el token del header
        var authHeader = request.getHeader("Authorization");

        if(authHeader != null){
            var token = authHeader.replace("Bearer ", "");

            System.out.println("token = " + token);


            //Consumir el token para obtener el nombre de usuario
            var subject = tokenService.getSubject(token);
            if(subject != null){
                //el sujeto no es null
                var usuario = usuarioRepository.findByLogin(subject);

                Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.getUsername(),
                        null,
                        usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        //Metodo para pasar al siguiente filtro
        filterChain.doFilter(request,response);
    }
}
