package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.entities.ProductoEntity;

public interface ProductoRepository extends MongoRepository<ProductoEntity,String> {
}
