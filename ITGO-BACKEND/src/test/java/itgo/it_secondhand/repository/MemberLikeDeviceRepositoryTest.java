package itgo.it_secondhand.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
@Transactional
class MemberLikeDeviceRepositoryTest {

    @Autowired
    MemberLikeDeviceRepository memberLikeDeviceRepository;


    @Test
    public void findMemberLikeDeviceTest() throws Exception {
        // given


        // when

        // then

    }

}