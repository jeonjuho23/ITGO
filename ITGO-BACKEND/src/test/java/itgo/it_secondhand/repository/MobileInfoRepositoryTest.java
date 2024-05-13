package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.MobileInfo;
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
class MobileInfoRepositoryTest {
    @Autowired
    MobileInfoRepository mobileInfoRepository;

    @Test
    public void saveMobileInfo() throws Exception {
        // given
        List<MobileInfo> data = new ArrayList<>();

//        MobileInfo.builder().



        // when
        List<MobileInfo> res = mobileInfoRepository.saveAll(data);

        // then
        assertEquals(res.size(), data.size(), "삽입한 데이터와 확인 데이터는 같습니다.");
    }


}