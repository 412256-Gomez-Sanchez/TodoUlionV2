package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tht.adaptive.ApiUlion.entities.PremioEntity;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity,String> {
    Optional<UsuarioEntity> findByNombreAndContrasenia(String nombre, String contrasenia);

    Optional<UsuarioEntity> findByTelefonoAndContrasenia(String telefono, String contrasenia);

    Optional<UsuarioEntity> findByEmpresa_Nombre(String empresaNombre);

    @Query("{ 'empresa': { $exists: true, $ne: null }, 'empresa.premios': { $not: { $size: 0 }, $ne: null } }")
    List<UsuarioEntity> findByEmpresaPremiosNotEmpty();
}
