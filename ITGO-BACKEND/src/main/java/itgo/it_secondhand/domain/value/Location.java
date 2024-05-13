package itgo.it_secondhand.domain.value;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
// 값 타입
public class Location {

    private String city;
    private String street;
    private String zipcode;

    protected Location(){}

    public Location(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
