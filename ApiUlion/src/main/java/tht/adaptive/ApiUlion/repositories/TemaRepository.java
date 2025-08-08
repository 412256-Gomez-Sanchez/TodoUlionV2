package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.entities.TemaEntity;

public interface TemaRepository extends MongoRepository<TemaEntity,String> {

}
