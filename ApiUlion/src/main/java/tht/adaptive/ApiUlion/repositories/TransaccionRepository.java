package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.entities.TransaccionEntity;

public interface TransaccionRepository extends MongoRepository<TransaccionEntity,String> {
}
