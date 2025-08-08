package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.entities.EmpresaEntity;

public interface EmpresaRepository extends MongoRepository<EmpresaEntity,String> {
}
