package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Anotacion para que Spring sepa que es un servicio
@Service
//Importante, esta clase sera la encargada de buscar el usuario en la base de datos a partir de su username
public class AutenticacionService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    //Notar que el query method devuelve no la clase Usuario, sino alguna clase que implemente UserDetails (interfaz)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
