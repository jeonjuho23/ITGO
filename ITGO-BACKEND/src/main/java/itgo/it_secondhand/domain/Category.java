package itgo.it_secondhand.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String manufacturer;
    private String deviceType;

    protected Category(){}

    private Category(String manufacturer, String deviceType){
        this.manufacturer = manufacturer;
        this. deviceType = deviceType;
    }

    public static Category createCategory(String manufacturer, String deviceType){
        return new Category(manufacturer, deviceType);
    }


}
