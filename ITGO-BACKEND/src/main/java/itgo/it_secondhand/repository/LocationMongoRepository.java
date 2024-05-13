package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.LocationMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationMongoRepository extends MongoRepository<LocationMongo, String> {
    List<LocationMongo> findAllByLocation_CityOrLocation_Street(String city, String street);
}
