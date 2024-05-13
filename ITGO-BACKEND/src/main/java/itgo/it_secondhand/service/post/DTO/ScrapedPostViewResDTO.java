package itgo.it_secondhand.service.post.DTO;

import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScrapedPostViewResDTO {
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
    private String detailId;

    private Boolean isLike;

    // 위치

    public ScrapedPostViewResDTO(Member member, SecondhandScrapedPost post, Device device, Boolean isLike){
        this.postId = post.getId();
        this.memberId = member.getId();
        this.deviceId = device.getId();
        this.title = post.getPostTitle();
        this.content = post.getPostContent();
        this.time = post.getPostTime();
        this.likeCount = post.getPostLikeCount();
        this.viewCount = post.getPostViewCount();
        this.scrapedUrl = post.getPostUrl();
        this.imgAddress = post.getImgFolderAddress();
        this.memberName = member.getName();
        this.deviceName = device.getDeviceName();
        this.manufacturer = device.getCategory().getManufacturer();
        this.detailId = device.getDetailId();
        this.isLike = isLike;

        // 위치
    }

}
