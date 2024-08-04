package itgo.it_secondhand.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Device {

    @Id @GeneratedValue
    @Column(name = "device_id")
    private Long id;

    private String deviceName;
    private int launchPrice;
    private LocalDateTime releaseDate;

    // 기기의 자세한 제원을 저장한 NoSQL DB의 id값
    @Column(name = "detail_id")
    private String detailId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


    private Device(String deviceName, int launchPrice, Category category, LocalDateTime releaseDate, String detailId){
        this.deviceName = deviceName;
        this.launchPrice = launchPrice;
        this.category = category;
        this.releaseDate = releaseDate;
        this.detailId = detailId;
    }

    //== 생성 메서드 ==//
    public static Device createDevice(String deviceName, int launchPrice, Category category, LocalDateTime releaseDate, String detailId) {
        return new Device(deviceName, launchPrice, category, releaseDate, detailId);
    }


}


