package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
import itgo.it_secondhand.domain.Notification;
import itgo.it_secondhand.domain.NotificationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    private Notification notification;

    @BeforeEach
    void setUp(){
        notification = getNotification();

        notificationRepository.save(notification);
    }


    @Test
    public void findByMemberId() throws Exception {
        //given

        //when
        Notification result = notificationRepository.findByMemberId(notification.getMemberId());

        //then
        assertThat(result.getMemberId())
                .isEqualTo(notification.getMemberId());
    }


    @Test
    public void deleteByMemberId() throws Exception {
        //given

        //when
        notificationRepository.deleteByMemberId(notification.getMemberId());

        //then
    }
}