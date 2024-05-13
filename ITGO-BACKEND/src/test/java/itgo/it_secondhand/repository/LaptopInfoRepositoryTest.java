package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.LaptopInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
@Transactional
class LaptopInfoRepositoryTest {
    @Autowired
    LaptopInfoRepository laptopInfoRepository;

    @Test
    public void saveLaptopInfo() throws Exception {
        // given
        List<LaptopInfo> data = new ArrayList<>();

        


        // when
        List<LaptopInfo> res = laptopInfoRepository.saveAll(data);


        // then
        assertEquals(data.size(), res.size(), "삽입한 데이터와 확인 데이터는 같습니다.");
    }


}