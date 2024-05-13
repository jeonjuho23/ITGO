package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.LocationMongo;
import itgo.it_secondhand.domain.value.Location;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
//@Rollback(value = false)
@Slf4j
class LocationMongoRepositoryTest {
    @Autowired
    LocationMongoRepository locationMongoRepository;

    @Test
    @Rollback(value = false)
    public void saveLocationData() throws Exception {
        // given
        List<LocationMongo> locationMongoList = new ArrayList<>();

        // 아산시
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "염치읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "배방읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "송악면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "탕정면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "음봉면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "둔포면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "영인면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "인주면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "선장면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "도고면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "신창면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양3동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양4동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양5동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("아산시", "온양6동", "")));

        // 용인시
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "풍덕천1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "풍덕천2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "신봉동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "죽전1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "죽전2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "죽전3동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "동천동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "상현1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "상현2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "상현3동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "성복동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "신갈동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "영덕1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "영덕2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "구갈동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "상갈동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "보라동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "기흥동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "서농동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "구성동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "마북동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "동백1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "동백2동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "동백3동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "포곡읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "모현읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "이동읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "남사읍", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "원삼면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "백암면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "양지면", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "중앙동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "역북동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "삼가동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "유림동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("용인시", "동부동", "")));

        // 기타 등등
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("금천구", "가산동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("수영구", "광안제1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("광진구", "중곡제1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("부천시", "부천동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("관악구", "서원동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("구로구", "구로구제1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("군포시", "산본1동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("사하구", "신평동", "")));
        locationMongoList.add(LocationMongo.createLocationMongo(new Location("권선구", "곡선동", "")));


        // when
        List<LocationMongo> res = locationMongoRepository.saveAll(locationMongoList);

        // then
        assertEquals(locationMongoList.size(), res.size(), "삽입된 데이터와 그 확인 데이터는 같다.");
    }

    @Test
    public void 전체삭제() throws Exception {
        // given

        // when
        locationMongoRepository.deleteAll();
        // then

    }


}