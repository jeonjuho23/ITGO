package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.LocationMongo;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class LocationMongoRepositoryTest {

    @Autowired
    LocationMongoRepository locationMongoRepository;

    @BeforeEach
    void setUp(){
        locationMongoRepository.save(
                LocationMongo.createLocationMongo(
                        new Location("city", "street", "zipcode")));
    }

    @Test
    public void findByKeyword() throws Exception {
        //given
        String city = "city";
        String street = "street";

        //when
        List<LocationMongo> result = locationMongoRepository.findAllByLocation_CityOrLocation_Street(city, street);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getLocation().getCity()).isEqualTo(city);
        assertThat(result.get(0).getLocation().getStreet()).isEqualTo(street);
    }
}