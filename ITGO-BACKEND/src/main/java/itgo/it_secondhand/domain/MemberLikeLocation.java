package itgo.it_secondhand.domain;

import itgo.it_secondhand.domain.value.Location;
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
public class MemberLikeLocation {

    @Id @GeneratedValue
    @Column(name = "member_like_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Location location;

    private LocalDateTime likeDate;

    private MemberLikeLocation(Location location, Member member){
        this.location = location;
        this.member = member;
        this.likeDate = LocalDateTime.now();
    }

    public static MemberLikeLocation createMemberLikeLocation(Location location, Member member){
        return new MemberLikeLocation(location, member);
    }
}
