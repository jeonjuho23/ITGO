package itgo.it_secondhand.service.post.DTO;

import itgo.it_secondhand.domain.value.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostResDTO {
    // 게시글 공통
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime time;
    private String memberName;
    private String imgFolderAddress;

    // 좋아요 게시글 구별
    private boolean isLike;

    // 중고 거래 게시글
    private Location location;
    private int price;
}
