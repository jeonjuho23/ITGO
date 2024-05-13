package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
@Transactional
class DeviceRepositoryTest {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MobileInfoRepository mobileInfoRepository;
    @Autowired
    LaptopInfoRepository laptopInfoRepository;

    @Test
    @Rollback(value = false)
    public void saveDeviceData() throws Exception {
        // given
        List<Device> data = new ArrayList<>();
        Category samMobile = categoryRepository.findByManufacturerAndDeviceType("삼성", "스마트폰");
        Category appleMobile = categoryRepository.findByManufacturerAndDeviceType("애플", "스마트폰");
        Category samTablet = categoryRepository.findByManufacturerAndDeviceType("삼성", "태블릿");
        Category appleTablet = categoryRepository.findByManufacturerAndDeviceType("애플", "태블릿");


        //== Mobile ==//

        //== Samsung

        // 갤럭시 S23
        data.add(Device.createDevice("갤럭시 S23",1155000,
                samMobile,
                LocalDateTime.of(2023, 2,17,0,0,0),
                "65744f12d2f015c973196256"));
        data.add(Device.createDevice("갤럭시 S23 울트라",1599400,
                samMobile,
                LocalDateTime.of(2023, 2,17,0,0,0),
                "65744e42d2f015c973196254"));
        data.add(Device.createDevice("갤럭시 S23 플러스",1353000,
                samMobile,
                LocalDateTime.of(2023, 2,17,0,0,0),
                "65744eb6d2f015c973196255"));
        data.add(Device.createDevice("갤럭시 S23 FE",847000,
                samMobile,
                LocalDateTime.of(2023, 12,8,0,0,0),
                "65744822d2f015c97319624e"));

        // 갤럭시 S22
        data.add(Device.createDevice("갤럭시 S22",999900,
                samMobile,
                LocalDateTime.of(2022, 2,25,0,0,0),
                "65745840d2f015c973196263"));
        data.add(Device.createDevice("갤럭시 S22 플러스",1199000,
                samMobile,
                LocalDateTime.of(2022, 2,25,0,0,0),
                "657457e2d2f015c973196262"));
        data.add(Device.createDevice("갤럭시 S22 울트라",1452000,
                samMobile,
                LocalDateTime.of(2022, 2,25,0,0,0),
                "6574576dd2f015c973196261"));

        // 갤럭시 S21
        data.add(Device.createDevice("갤럭시 S21",999900,
                samMobile,
                LocalDateTime.of(2021, 1,29,0,0,0),
                ""));
        data.add(Device.createDevice("갤럭시 S21 플러스",1199000,
                samMobile,
                LocalDateTime.of(2021, 1,29,0,0,0),
                ""));
        data.add(Device.createDevice("갤럭시 S21 울트라",1452000,
                samMobile,
                LocalDateTime.of(2021, 1,29,0,0,0),
                ""));

        // 갤럭시 S20
        data.add(Device.createDevice("갤럭시 S20",1248500,
                samMobile,
                LocalDateTime.of(2020, 10,16,0,0,0),
                ""));
        data.add(Device.createDevice("갤럭시 S20 플러스",1353000,
                samMobile,
                LocalDateTime.of(2020, 10,16,0,0,0),
                ""));
        data.add(Device.createDevice("갤럭시 S20 울트라",1595000,
                samMobile,
                LocalDateTime.of(2020, 10,16,0,0,0),
                ""));
        data.add(Device.createDevice("갤럭시 S20 FE",899800,
                samMobile,
                LocalDateTime.of(2022, 4,1,0,0,0),
                ""));

        // 갤럭시 Z 폴드
        data.add(Device.createDevice("갤럭시 Z 폴드 5",2097700,
                samMobile,
                LocalDateTime.of(2023, 8,11,0,0,0),
                "65744af9d2f015c973196250"));
        data.add(Device.createDevice("갤럭시 Z 폴드 4",1998700,
                samMobile,
                LocalDateTime.of(2022, 8,10,0,0,0),
                "65745093d2f015c973196258"));
        data.add(Device.createDevice("갤럭시 Z 폴드 3",1899700,
                samMobile,
                LocalDateTime.of(2021, 8,11,0,0,0),
                ""));

        // 갤럭시 Z 플립
        data.add(Device.createDevice("갤럭시 Z 플립 5",1399200,
                samMobile,
                LocalDateTime.of(2023, 8,11,0,0,0),
                "65744b93d2f015c973196251"));
        data.add(Device.createDevice("갤럭시 Z 플립 4",1353000,
                samMobile,
                LocalDateTime.of(2022, 8,26,0,0,0),
                "65745135d2f015c973196259"));
        data.add(Device.createDevice("갤럭시 Z 플립 3",1254000,
                samMobile,
                LocalDateTime.of(2021, 8,27,0,0,0),
                ""));


        //== Apple

        // 아이폰 15
        data.add(Device.createDevice("아이폰 15",1400000,
                appleMobile,
                LocalDateTime.of(2023, 10,13,0,0,0),
                "65740dedd2f015c973196242"));
        data.add(Device.createDevice("아이폰 15 플러스",1500000,
                appleMobile,
                LocalDateTime.of(2023, 10,13,0,0,0),
                "65740d4cd2f015c973196241"));
        data.add(Device.createDevice("아이폰 15 프로",1700000,
                appleMobile,
                LocalDateTime.of(2023, 10,13,0,0,0),
                "65740c8fd2f015c973196240"));
        data.add(Device.createDevice("아이폰 15 프로 맥스",1900000,
                appleMobile,
                LocalDateTime.of(2023, 10,13,0,0,0),
                "657409cbd2f015c97319623f"));

        // 아이폰 14
        data.add(Device.createDevice("아이폰 14",1400000,
                appleMobile,
                LocalDateTime.of(2022, 10,7,0,0,0),
                "65741024d2f015c973196245"));
        data.add(Device.createDevice("아이폰 14 플러스",1500000,
                appleMobile,
                LocalDateTime.of(2022, 10,7,0,0,0),
                "65740fa5d2f015c973196244"));
        data.add(Device.createDevice("아이폰 14 프로",1700000,
                appleMobile,
                LocalDateTime.of(2022, 10,7,0,0,0),
                ""));
        data.add(Device.createDevice("아이폰 14 프로 맥스",1900000,
                appleMobile,
                LocalDateTime.of(2022, 10,7,0,0,0),
                "65740edad2f015c973196243"));

        // 아이폰 13
        data.add(Device.createDevice("아이폰 13",1230000,
                appleMobile,
                LocalDateTime.of(2021, 9,15,0,0,0),
                "6574124bd2f015c973196248"));
        data.add(Device.createDevice("아이폰 13 미니",1090000,
                appleMobile,
                LocalDateTime.of(2021, 9,15,0,0,0),
                "657412e6d2f015c973196249"));
        data.add(Device.createDevice("아이폰 13 프로",1490000,
                appleMobile,
                LocalDateTime.of(2021, 9,15,0,0,0),
                "657411d2d2f015c973196247"));
        data.add(Device.createDevice("아이폰 13 프로 맥스",1630000,
                appleMobile,
                LocalDateTime.of(2021, 9,15,0,0,0),
                "65741160d2f015c973196246"));

        // 아이폰 SE
        data.add(Device.createDevice("아이폰 SE 3",730000,
                appleMobile,
                LocalDateTime.of(2022, 3,8,0,0,0),
                ""));
        data.add(Device.createDevice("아이폰 SE 2", 620000,
                appleMobile,
                LocalDateTime.of(2020, 4,15,0,0,0),
                ""));


        //== 태블릿 ==//

        //== Samsung
        data.add(Device.createDevice("갤럭시 탭",620000,
                samTablet,
                LocalDateTime.of(2023, 2,17,0,0,0),
                ""));

        //== Apple
        data.add(Device.createDevice("아이패드",620000,
                appleTablet,
                LocalDateTime.of(2023, 2,17,0,0,0),
                ""));


        // when
        List<Device> res = deviceRepository.saveAllAndFlush(data);

        // then
        assertEquals(res.size(), data.size(), "삽입한 데이터와 확인 데이터는 같습니다.");
    }



}