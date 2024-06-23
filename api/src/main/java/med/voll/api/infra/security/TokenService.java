package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //El secreto es la firma del token. Es como un certificado para poder ver si el token es legitimo y fue emitido
    // por la organizacion
    @Value("${api.security.secret}")
    private String apiSecret;

    //Para este metodo aprovechamos la liberaria JWT de Auth0
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return  JWT.create()
                    .withIssuer("vollmed")  //Organizacion que firma el token
                    .withSubject(usuario.getLogin()) //username
                    .withClaim("id",usuario.getId()) //campo id (opcional)
                    .withExpiresAt(generarFechaExp()) // expiracion del token
                    .sign(algorithm); //finalizacion del metodo
        } catch (JWTCreationException exception){
            throw new RuntimeException("Hubo un error al crear el JWT");
        }
    }

    //Consumir token para obtener el username del token
    public String getSubject(String token){

        DecodedJWT decodedJWT = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            decodedJWT = JWT.require(algorithm)
                    .withIssuer("vollmed")
                    .build()
                    .verify(token);

        } catch (JWTVerificationException exception){
            exception.getMessage();
        }

        if(decodedJWT.getSubject() == null){
            throw new RuntimeException("Subject nulo");
        }

        return decodedJWT.getSubject();
    }

    //Objeto instant para fijar una fecha de expiracion
    private Instant generarFechaExp(){
        //El token solo tiene validez por 2 horas usando de referencia la zona horaria de Peru GMT -5
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
