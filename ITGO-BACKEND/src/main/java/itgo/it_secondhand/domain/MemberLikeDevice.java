package itgo.it_secondhand.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLikeDevice {

    @Id @GeneratedValue
    @CollectionTable(name = "member_like_device_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "like_date")
    private LocalDateTime likeDate;

    //== 생성자 ==//
    private MemberLikeDevice(Member member, Device device) {
        this.member = member;
        this.device = device;
        this.likeDate = LocalDateTime.now();
    }


    //== 연관관계 메서드 ==//
//    public void setMember(Member member) {
////        this.member = member;
//        member.getLikeDeviceList().add(this);
//    }
//    public void delete(Member member){
//        this.member = member;
//        member.getLikeDeviceList().remove(this);
//    }



    //== 생성 메서드 ==//
    public static MemberLikeDevice createMemberLikeDevice(Member member, Device device){
        return new MemberLikeDevice(member, device);
    }





}
