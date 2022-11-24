package proyecto.finalJA.FPseguridad.Controladores;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proyecto.finalJA.FPseguridad.modelos.Permiso;
import proyecto.finalJA.FPseguridad.modelos.PermisosRol;
import proyecto.finalJA.FPseguridad.modelos.Rol;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioPermiso;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioPermisosRol;
import proyecto.finalJA.FPseguridad.repositorios.RepositorioRol;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/permisos-rol")
public class ControladorPermisosRol {


    @Autowired
    private RepositorioPermisosRol miRepositorioPermisosRol;

    @Autowired
    private RepositorioRol miRepositorioRol;

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;


    @GetMapping
    public List<PermisosRol> buscarTodosLosPermisosRoles(){
        log.info("Buscando todos los permisos roles en base de datos...");
        return miRepositorioPermisosRol.findAll();
    }

    //relacion muchos a muchos
    @PostMapping("rol/{idRol}/permiso/{idPermiso}")
    public PermisosRol crearPermisosRol(@PathVariable String idRol,@PathVariable String idPermiso){

        Rol rol= miRepositorioRol.findById(idRol).orElse(null);
        Permiso permiso= miRepositorioPermiso.findById(idPermiso).orElse(null);

        if(rol != null && permiso != null){

            PermisosRol permisosRol= new PermisosRol(rol,permiso);
            return miRepositorioPermisosRol.save(permisosRol);

        }else{
            return null;
        }

    }

    @GetMapping("/validar-permiso/rol/{idRol}")
    public PermisosRol validarPermiso(@PathVariable String idRol, @RequestBody Permiso infoPermiso, HttpServletResponse response) throws IOException {
        log.info("permiso que llega: {}",infoPermiso);

        //Buscar en base de datos el rol y permiso
        Rol rolActual=miRepositorioRol.findById(idRol).orElse(null);
        Permiso permisoActual=miRepositorioPermiso.findByurlAndMethod(infoPermiso.getUrl(),infoPermiso.getMetodo());

        //Validar si existe el rol y el permiso en base de datos
        if(rolActual != null && permisoActual != null){

            String idRolActual=rolActual.get_id();
            String idPermisoActual= permisoActual.get_id();
            log.info("idRolActual: {}, idPermisoActual: {}", idRolActual, idPermisoActual);

            //Buscar en la tabla PermisosRol si el rol tiene asociado el permiso.
            PermisosRol permisosRolActual=miRepositorioPermisosRol.findByRolAndPermiso(idRolActual,idPermisoActual);
            log.info("El permisosRol que encontró en BD fue: {}", permisosRolActual);

            if(permisosRolActual != null){
                return permisosRolActual;

            }else{
                log.error("NO se encuentra el PermisosRol en base de datos");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return null;
            }
        }else{
            log.error("NO se encuentra el rol o el permiso en la base de datos");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

    }






}
