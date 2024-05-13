package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.LaptopInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopInfoRepository extends MongoRepository<LaptopInfo, String> {
}
