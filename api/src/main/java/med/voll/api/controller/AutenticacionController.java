package med.voll.api.controller;

import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DTOJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DTOJWT> autenticacionUsuario(@RequestBody DatosAutenticacionUsuario datosAutenticacionUsuario){

        //Generar un token de autenticacion a partir de el username y la clave obtenidos de datosAutenticacionUsuario
        //(intento de login)
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());

        //El authenticationManager usa el metodo .authenticate() y el objeto UsernamePasswordAuthenticationToken
        //para autenticar al usuario
        // Se usa algun algoritmo de hashing Brycpt para comparar las contraseñas
        Usuario usuarioAuth = (Usuario) authenticationManager.authenticate(authenticationToken).getPrincipal();


        //Si la autenticacion en authenticate es exitosa, se actualiza el objeto y se retorna el objeto Authentication
        // y puedes acceder al Usuario autenticado con el metodo
        //.getPrincipal() el casteo es necesario porque se devuelve un Object


        //Lo que devolvemos al usuario correctamente logueado es un JWToken (json web token)
        var JWToken = tokenService.generarToken(usuarioAuth);

        return ResponseEntity.ok(new DTOJWT(JWToken));
    }
}
