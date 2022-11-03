package proyecto.finalJA.FPseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import proyecto.finalJA.FPseguridad.modelos.Usuario;

public interface RepositorioUsuario extends MongoRepository<Usuario, String> {

}
