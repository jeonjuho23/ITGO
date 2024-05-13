package itgo.it_secondhand.api.DTO.like;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindLikeListResponseDTO<T> {
    private T findList;
}
