package itgo.it_secondhand.service.post.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FindPostResDTO {
    private List<FindPostDTO> posts;
    private Boolean hasNext;
}
