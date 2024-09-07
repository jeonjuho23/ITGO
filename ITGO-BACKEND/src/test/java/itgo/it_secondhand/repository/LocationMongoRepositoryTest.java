package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
import itgo.it_secondhand.domain.LocationMongo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class LocationMongoRepositoryTest {

    @Autowired
    LocationMongoRepository locationMongoRepository;

    private LocationMongo locationMongo;

    @BeforeEach
    void setUp(){
        locationMongo = getLocationMongo();

        locationMongoRepository.save(locationMongo);
    }


    @Test
    public void findByKeyword() throws Exception {
        //given
        String city = locationMongo.getLocation().getCity();
        String street = locationMongo.getLocation().getStreet();

        //when
        List<LocationMongo> result = locationMongoRepository.findAllByLocation_CityOrLocation_Street(city, street);

        //then
        assertThat(result.get(0).getLocation().getCity())
                .isEqualTo(city);
        assertThat(result.get(0).getLocation().getStreet())
                .isEqualTo(street);
    }
}