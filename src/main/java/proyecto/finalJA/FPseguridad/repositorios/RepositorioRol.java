package proyecto.finalJA.FPseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.finalJA.FPseguridad.modelos.Rol;

public interface RepositorioRol extends MongoRepository<Rol, String> {
}
