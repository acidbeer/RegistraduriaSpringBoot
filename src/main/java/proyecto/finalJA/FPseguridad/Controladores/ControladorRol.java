package proyecto.finalJA.FPseguridad.Controladores;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import proyecto.finalJA.FPseguridad.modelos.Rol;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioRol;

import java.util.List;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rol")
public class ControladorRol {

    @Autowired
    private RepositorioRol mirepositorioRol;

    @GetMapping
    public List<Rol> buscarTodosLosRoles(){
        log.info("buscando todos lo roles..");
        return mirepositorioRol.findAll();
    }

    @GetMapping("{idRol}")
    public Rol buscarRol(@PathVariable String idRol){
        return mirepositorioRol
                .findById(idRol)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{idRol}")
    public void eliminarRol(@PathVariable String idRol){
        Rol rolActual= mirepositorioRol
                .findById(idRol)
                .orElse(null);

        if(rolActual != null){
            mirepositorioRol.delete(rolActual);
        }
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol crearRol(@RequestBody Rol infoRol){
        return mirepositorioRol.save(infoRol);
    }

    @PutMapping("{idRol}")
    public Rol modificarRol(@PathVariable String idRol,@RequestBody Rol infoRol){
        log.info("modificando el rol: {}", idRol);
        Rol rolActual = mirepositorioRol
                .findById(idRol)
                .orElse(null);
        log.info("Permiso encontrado en base de datos: {}", rolActual);

        if(rolActual != null){
            rolActual.setDescripcion(infoRol.getDescripcion());
            rolActual.setNombre(infoRol.getNombre());

            return mirepositorioRol.save(rolActual);
        }else{
            return null;
        }
    }


}
