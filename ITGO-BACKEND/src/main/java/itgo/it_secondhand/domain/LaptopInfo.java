package itgo.it_secondhand.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("laptop")
@Builder
@Data
public class LaptopInfo {
    private String id;
    private String modelname;
}
