package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.MobileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MobileInfoRepository extends MongoRepository<MobileInfo, String> {
}
