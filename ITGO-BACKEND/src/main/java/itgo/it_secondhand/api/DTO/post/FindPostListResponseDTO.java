package itgo.it_secondhand.api.DTO.post;

import itgo.it_secondhand.service.post.DTO.FindPostDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindPostListResponseDTO {
    private List<FindPostDTO> posts;
    private Boolean hasNext;
}
