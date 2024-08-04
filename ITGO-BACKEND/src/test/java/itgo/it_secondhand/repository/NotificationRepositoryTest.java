package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Notification;
import itgo.it_secondhand.domain.NotificationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    private static final Long memberId = 1L;
    @BeforeEach
    void setUp(){
        List<NotificationMessage> messages = new ArrayList<>();
        messages.add(new NotificationMessage("message", LocalDateTime.now()));

        Notification newNotification = Notification.builder()
                .messages(messages)
                .memberId(memberId)
                .build();

        notificationRepository.save(newNotification);
    }


    @Test
    public void findByMemberId() throws Exception {
        //given

        //when
        Notification result = notificationRepository.findByMemberId(memberId);

        //then
        assertThat(result.getMemberId()).isEqualTo(memberId);
    }


    @Test
    public void deleteByMemberId() throws Exception {
        //given

        //when
        notificationRepository.deleteByMemberId(memberId);

        //then
    }
}