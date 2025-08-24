package tht.adaptive.ApiUlion.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tht.adaptive.ApiUlion.DTOs.PremioDto;
import tht.adaptive.ApiUlion.entities.PremioEntity;

import java.util.List;

public interface PremioRepository extends MongoRepository<PremioEntity,String> {
}
