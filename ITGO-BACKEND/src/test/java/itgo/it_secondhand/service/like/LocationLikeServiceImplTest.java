package itgo.it_secondhand.service.like;

import itgo.it_secondhand.service.like.DTO.LocationResDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
//@Rollback(value = false)
@Slf4j
class LocationLikeServiceImplTest {
    @Autowired
    LocationLikeServiceImpl locationLikeService;


    @Test
    public void findLocationByKeyword() throws Exception {
        // given
        String keyword = "염치읍";

        // when
        List<LocationResDTO<String>> byKeyword = locationLikeService.findByKeyword(keyword);

        // then
        assertEquals(byKeyword.size(), 1, "1 개를 특정해서 검색했음.");
    }

    @Test
    public void findMemberLikeLocationList() throws Exception {
        // given
        Long memberId = 2L;

        // when
        List<LocationResDTO<Long>> locationResDTOS = locationLikeService.checkList(memberId);


        // then
        assertEquals(locationResDTOS.size(), 0, "아직 좋아요 한 게 없습니다.");
    }

}