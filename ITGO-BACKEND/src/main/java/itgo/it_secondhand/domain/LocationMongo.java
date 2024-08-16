package itgo.it_secondhand.domain;

import itgo.it_secondhand.domain.value.Location;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
public class LocationMongo {

    @Id
    private String id;

    @Embedded
    private Location location;

    private LocationMongo(Location location){
        this.location = location;
    }

    public static LocationMongo createLocationMongo(Location location){
        return new LocationMongo(location);
    }

}
