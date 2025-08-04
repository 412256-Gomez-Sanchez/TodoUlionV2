package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tht.adaptive.ApiUlion.entities.PreguntaEntity;

import java.util.List;
import java.util.Optional;

public interface PreguntaRepository extends MongoRepository<PreguntaEntity,String> {
    @Query(value = "{ '_id': ?0 }",
            fields = "{ 'modificaciones': 1}")
    Optional<PreguntaEntity> findModificacionesById(String id);

    @Aggregation(pipeline = {
            "{ $match: { 'temas': ?0, '_id': { $nin: ?1 }, 'estaDesactivada': {$ne: true} } }",
            "{ $sample: { size: 1 } }"
    })
    Optional<PreguntaEntity> findRandomByTemaSampleExcludingIds(String tema, List<String> idsAExcluir);
}
