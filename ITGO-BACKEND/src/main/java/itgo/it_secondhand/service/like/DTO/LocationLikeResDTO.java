package itgo.it_secondhand.service.like.DTO;

import itgo.it_secondhand.domain.value.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationLikeResDTO {
    private Long likeId;
    private Location location;
}
