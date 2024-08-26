package itgo.it_secondhand.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "d_type")
@SuperBuilder
public abstract class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 200)
    private String postTitle;

    @Column(length = 2000)
    private String postContent;

    private LocalDateTime postTime;
    private LocalDateTime postUpdateTime;
    private int postLikeCount;
    private int postViewCount;

    @Column(length = 2000)
    private String imgFolderAddress;


    //== 생성자 ==//
    public Post(Member member, String postTitle, String postContent, LocalDateTime createTime, LocalDateTime updateTime, int postLikeCount, int postViewCount, String imgFolderAddress) {
        this.member = member;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postTime = createTime;
        this.postUpdateTime = updateTime;
        this.postLikeCount = postLikeCount;
        this.postViewCount = postViewCount;
        this.imgFolderAddress = imgFolderAddress;
    }


    //== 연관관계 메서드 ==//

//    public void setMember(Member member) {
//        this.member = member;
//        member.getMyPostList().add(this);
//    }


    //== 비즈니스 로직 ==//
    public void reduceLikeCount() {
        this.postLikeCount -= 1;
    }

    public void increaseLikeCount() {
        this.postLikeCount += 1;
    }

    public void increaseViewCount() {
        this.postViewCount += 1;
    }


}
