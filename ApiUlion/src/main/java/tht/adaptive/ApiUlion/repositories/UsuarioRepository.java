package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tht.adaptive.ApiUlion.entities.EmpresaEntity;
import tht.adaptive.ApiUlion.entities.PremioEntity;
import tht.adaptive.ApiUlion.entities.UsuarioEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity,String> {
    Optional<UsuarioEntity> findByNombreAndContrasenia(String nombre, String contrasenia);

    Optional<UsuarioEntity> findByTelefonoAndContrasenia(String telefono, String contrasenia);

    Optional<UsuarioEntity> findByEmpresa_Nombre(String empresaNombre);

    @Aggregation(pipeline = {
            // 1. Filtrar los documentos de usuario que tienen una empresa y premios
            "{ '$match': { 'empresa.premios': { '$not': { '$size': 0 }, '$ne': null }, 'empresa': { '$exists': true, '$ne': null } } }",

            // 2. Proyectar los documentos, pero filtrando los premios dentro del array 'premios'
            "{ '$project': {'empresa': { 'nombre': '$empresa.nombre', 'nombre_logo':'$empresa.nombre_logo', 'detalles': '$empresa.detalles', 'premios': { '$filter': { 'input': '$empresa.premios', 'as': 'premio', 'cond': { '$and': [ { '$lte': [ '$$premio.fecha_inicio', ?0 ] }, { '$gt': [ '$$premio.fecha_fin', ?0 ] } ] } } } } } }",

            // 3. (Opcional) Filtrar de nuevo para solo mostrar usuarios que tienen al menos un premio activo
            "{ '$match': { 'empresa.premios': { '$not': { '$size': 0 }, '$ne': null } } }"
    })
    List<UsuarioEntity> findByEmpresaPremiosNotEmpty(LocalDate fechaActual);

    @Aggregation(pipeline = {
            // 1. Filter documents with a company and awards.
            "{ '$match': { 'empresa.premios': { '$not': { '$size': 0 }, '$ne': null }, 'empresa': { '$exists': true, '$ne': null } } }",

            // 2. Project documents, filtering for active awards.
            "{ '$project': {'empresa': { 'nombre': '$empresa.nombre', 'nombre_logo':'$empresa.nombre_logo', 'detalles': '$empresa.detalles', 'premios': { '$filter': { 'input': '$empresa.premios', 'as': 'premio', 'cond': { '$and': [ { '$lte': [ '$$premio.fecha_inicio', ?0 ] }, { '$gt': [ '$$premio.fecha_fin', ?0 ] } ] } } } } } }",

            // 3. Filter out users without active awards.
            "{ '$match': { 'empresa.premios': { '$not': { '$size': 0 }, '$ne': null } } }",

            // 4. Randomly select one document.
            "{ '$sample': { 'size': 1 } }",

            // 5. Unwind the 'premios' array.
            "{ '$unwind': '$empresa.premios' }",

            // 6. Randomly select one unwound document (a single award).
            "{ '$sample': { 'size': 1 } }",

            // 7. Reconstruct the document with the original _id.
            "{ '$group': { '_id': '$_id', 'empresa': { '$first': '$empresa' }, 'premios': { '$push': '$empresa.premios' } } }",

            // 8. Re-project the single award back into the 'premios' list inside 'empresa'.
            "{ '$project': { 'empresa.premios': '$premios', 'empresa.nombre': '$empresa.nombre', 'empresa.nombre_logo': '$empresa.nombre_logo', 'empresa.detalles': '$empresa.detalles' } }",

//            // 9. Replace the root document with the 'empresa' sub-document.
//            "{ '$replaceRoot': { 'newRoot': '$empresa' } }"
    })
    Optional<UsuarioEntity> findRandomEmpresaWithRandomPremio(LocalDate fechaActual);
}
