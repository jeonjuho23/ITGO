package itgo.it_secondhand.service.post.DTO;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.domain.value.Location;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindPostDTO {
    private Long postId;
    private String title;
    private String memberName;
    private Location location;
    private int price;
    private String imgFolderAddress;
    private LocalDateTime postTime;
    private Boolean isView;
    private Boolean isLike;

    public FindPostDTO(Member member, SecondhandScrapedPost post, Boolean isView, Boolean isLike){
        this.postId = post.getId();
        this.title = post.getPostTitle();
        this.memberName = member.getName();
        this.location = post.getLocation();
        this.price = post.getSecondhandPrice();
        this.imgFolderAddress = post.getImgFolderAddress();
        this.postTime = post.getPostTime();
        this.isLike = isLike;
        this.isView = isView;
    }
}
