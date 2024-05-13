package itgo.it_secondhand.service.post.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostViewReqDTO {
    private Long memberId;
    private Long postId;
}
