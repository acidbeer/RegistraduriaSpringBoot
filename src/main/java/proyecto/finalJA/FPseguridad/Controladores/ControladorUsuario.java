package proyecto.finalJA.FPseguridad.Controladores;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import proyecto.finalJA.FPseguridad.modelos.Usuario;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioUsuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class ControladorUsuario {

    @Autowired
    private RepositorioUsuario miRepositorioUsuario;

    @GetMapping
    public List<Usuario> buscarTodoslosUsusarios(){
        log.info("buscando todos los usuarios...");
        return miRepositorioUsuario.findAll();
    }

    @GetMapping("{idUsuario}")
    public Usuario buscarUsuario(@PathVariable String idUsuario){
        return miRepositorioUsuario
                .findById(idUsuario)
                .orElse(new Usuario("","",""));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{idUsuario}")
    public void elimiarUsuario(@PathVariable String idUsuario){
        Usuario usuarioActual= miRepositorioUsuario
                .findById(idUsuario)
                .orElse(null);
        if(usuarioActual != null){
            miRepositorioUsuario.delete(usuarioActual);
        }

    }




    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario infoUsuario){
        String contrasenaCifrada=convertirSHA256(infoUsuario.getContrasena());
        infoUsuario.setContrasena(contrasenaCifrada);
        return this.miRepositorioUsuario.save(infoUsuario);

    }

    @PutMapping("{idUsuario}")
    public Usuario modificarUsuario(@PathVariable String idUsuario,@RequestBody Usuario  infoUsuario){

        log.info("modificando el usuario: {}", idUsuario);
        Usuario usuarioActual = miRepositorioUsuario
                .findById(idUsuario)
                .orElse(null);
        log.info("Usuario encontrado en base de datos: {}", usuarioActual);

        if(usuarioActual != null){
            usuarioActual.setCorreo(infoUsuario.getCorreo());
            usuarioActual.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            usuarioActual.setSeudonimo(infoUsuario.getSeudonimo());

            return miRepositorioUsuario.save(usuarioActual);
        }else{
            return null;
        }

    }

    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


}
