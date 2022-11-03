package proyecto.finalJA.FPseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.finalJA.FPseguridad.modelos.Permiso;

public interface RepositorioPermiso extends MongoRepository<Permiso,String> {

}
