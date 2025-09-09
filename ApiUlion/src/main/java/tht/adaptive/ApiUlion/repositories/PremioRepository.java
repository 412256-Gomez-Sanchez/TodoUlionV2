package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.DTOs.PremioDto;
import tht.adaptive.ApiUlion.entities.PremioEntity;

import java.util.List;
import java.util.Optional;

public interface PremioRepository extends MongoRepository<PremioEntity,String> {
    @Aggregation(pipeline = {
            "{ $match: { 'temas': ?0, '_id': { $nin: ?1 }, 'estaDesactivada': {$ne: true} } }",
            "{ $sample: { size: 1 } }"
    })
    Optional<PremioEntity> findRandomExludinIds(List<String> ids);
}
