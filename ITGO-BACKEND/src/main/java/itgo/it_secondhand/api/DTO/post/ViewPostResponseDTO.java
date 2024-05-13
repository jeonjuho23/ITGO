package itgo.it_secondhand.api.DTO.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ViewPostResponseDTO {
    private Long postId;
    private Long memberId;
    private Long deviceId;

    private String title;
    private String content;
    private LocalDateTime time;
    private int likeCount;
    private int viewCount;
    private String scrapedUrl;
    private String imgAddress;

    private String memberName;

    private String deviceName;
    private String manufacturer;
    private Long detailId;

    private Boolean isLike;

    // 위치
}
