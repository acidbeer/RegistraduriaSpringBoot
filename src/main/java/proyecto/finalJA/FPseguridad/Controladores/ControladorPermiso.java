package proyecto.finalJA.FPseguridad.Controladores;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import proyecto.finalJA.FPseguridad.modelos.Permiso;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioPermiso;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/permiso")
public class ControladorPermiso {

    @Autowired
    private RepositorioPermiso mirepositorioPermiso;

    @GetMapping
    public List<Permiso> buscarTodosLosPermisos(){
        log.info("buscando todos lo Permisos..");
        return mirepositorioPermiso.findAll();
    }

    @GetMapping("{idPermiso}")
    public Permiso buscarPermiso(@PathVariable String idPermiso){
        return mirepositorioPermiso
                .findById(idPermiso)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{idPermiso}")
    public void eliminarPermiso(@PathVariable String idPermiso){
        Permiso permisoActual= mirepositorioPermiso
                .findById(idPermiso)
                .orElse(null);
        if(permisoActual != null){
            mirepositorioPermiso.delete(permisoActual);
        }

    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso crearPermiso(@RequestBody Permiso infoPermiso){
        return mirepositorioPermiso.save(infoPermiso);
    }

    @PutMapping("{idPermiso}")
    public Permiso modificarPermiso(@PathVariable String idPermiso, @RequestBody Permiso infoPermiso){
        log.info("modificando el Permiso: {}", idPermiso);
        Permiso permisoActual= mirepositorioPermiso
                .findById(idPermiso)
                .orElse(null);
        log.info("Permiso encontrado en base de datos: {}", permisoActual);

        if(permisoActual != null){

            permisoActual.setUrl(infoPermiso.getUrl());
            permisoActual.setMetodo(infoPermiso.getMetodo());

            return mirepositorioPermiso.save(permisoActual);
        }else{
            return null;
        }
    }


}
