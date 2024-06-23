package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //anotacion para indicar que esta clase es de configuracion
@EnableWebSecurity //Del tipo de seguridad web

//Esta clase es importante porque por defecto Spring Security configura la aplicacion para ser STATEFUL
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    //Los siguientes metodos seran importantes para configurar autenticacion STATELESS

    //Bean para deshabilitar el CSRF que se usa para aplicaciones STATEFUL, lo cual no es el contexto de este proyecto
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //Se configura la autenticacion de manera STATELESS (No se guardan el estado del login del usuario)
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                        )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Es necesario tambien proporcionarle a Spring el authentication Manager, sin esto no es posible gestionar
    //la autenticacion
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Ademas le proporcionamos a Spring el Bean que se usará para el cifrado y descifrado de contraseñas
    //Algoritmo BCrypt
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
