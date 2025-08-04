package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity,String> {
    Optional<UsuarioEntity> findByNombreAndContrasenia(String nombre, String contrasenia);
    Optional<UsuarioEntity> findByTelefonoAndContrasenia(String telefono, String contrasenia);
}
