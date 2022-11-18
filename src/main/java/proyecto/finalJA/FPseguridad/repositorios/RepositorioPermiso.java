package proyecto.finalJA.FPseguridad.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import proyecto.finalJA.FPseguridad.modelos.Permiso;

public interface RepositorioPermiso extends MongoRepository<Permiso,String> {

    @Query("{'url': ?0, 'metodo': ?1}")
    public Permiso findByurlAndMethod(String url,String metodo);

}
