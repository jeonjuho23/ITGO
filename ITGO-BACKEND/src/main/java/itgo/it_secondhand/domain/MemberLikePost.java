package itgo.it_secondhand.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class MemberLikePost {

    @Id @GeneratedValue
    @Column(name = "member_like_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime likeDate;

    //== 생성자 ==//
    private MemberLikePost(Member member, Post post){
        this.member = member;
        this.post = post;
        this.likeDate = LocalDateTime.now();
    }


    //== 연관관계 메서드 ==//
//    public void setMember(Member member) {
//        this.member = member;
//        member.getLikePostList().add(this);
//    }


    //== 생성 메서드 ==//
    public static MemberLikePost createMemberLikePost(Member member, Post post) {
        // 좋아요 카운트 증가
        post.increaseLikeCount();
        return new MemberLikePost(member, post);
    }


}
