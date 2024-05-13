package itgo.it_secondhand.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberViewPost {

    @Id @GeneratedValue
    @Column(name = "member_view_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private int viewCount;

    private LocalDateTime viewDate;

    //== 생성자 ==//
    private MemberViewPost(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.viewDate = LocalDateTime.now();
        this.viewCount = 1;
        // Post 테이블 조회수 증가
        this.post.increaseViewCount();
    }


    //== 연관관계 메서드 ==//
//    public void setMember(Member member) {
//        this.member = member;
//        member.getViewPostList().add(this);
//    }


    //== 생성 메서드 ==//
    public static MemberViewPost createMemberViewPost(Member member, Post post) {
        return new MemberViewPost(member, post);
    }

    //== 비즈니스 로직 ==//
    public void increaseViewCount(){
        this.viewDate = LocalDateTime.now();
        this.viewCount += 1;
        this.post.increaseViewCount();
    }

}
