package itgo.it_secondhand.domain;


import itgo.it_secondhand.domain.value.Location;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String phone;
    private String name;
    private String imgAddress;

    @Embedded
    private Location location;

    private Member(String phone, String name, String imgAddress, Location location) {
        this.phone = phone;
        this.name = name;
        this.imgAddress = imgAddress;
        this.location = location;
    }

//    // 연관관계 세팅
//    @OneToMany(mappedBy = "member")
//    private List<MemberLikeDevice> likeDeviceList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    private List<MemberLikePost> likePostList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    private List<MemberViewPost> viewPostList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    private List<Post> myPostList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    private List<MemberSearchKeyword> searchList = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Member createMember(String phone, String name, String imgAddress, Location location) {
        return new Member(phone, name, imgAddress, location);
    }

    public void updateUser(Member member) {
        if(member.getPhone() != null){
            this.phone=member.getPhone();
        }
        if(member.getName() != null){
            this.name=member.getName();
        }
        if(member.getLocation() != null){
            this.location=member.getLocation();
        }
        if(member.getImgAddress() != null){
            this.imgAddress=member.getImgAddress();
        }
    }

}
