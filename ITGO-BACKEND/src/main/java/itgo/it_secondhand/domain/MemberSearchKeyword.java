package itgo.it_secondhand.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchKeyword {

    @Id @GeneratedValue
    @Column(name = "member_search_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    private LocalDateTime searchDate;

    //== 생성자 ==//
    private MemberSearchKeyword(Member member, Keyword keyword) {
        this.member = member;
        this.keyword = keyword;
        this.searchDate = LocalDateTime.now();
    }


    //== 연관관계 메서드 ==//
//    public void setMember(Member member) {
//        this.member = member;
//        member.getSearchList().add(this);
//    }


    //== 생성 메서드 ==//
    public static MemberSearchKeyword createMemberSearchKeyword(Member member, Keyword keyword) {
        return new MemberSearchKeyword(member, keyword);
    }

    //== 비즈니스 메서드 ==//
    public int increaseSearchCount(){
        this.searchDate = LocalDateTime.now();
        return this.keyword.increaseCount();
    }

}
