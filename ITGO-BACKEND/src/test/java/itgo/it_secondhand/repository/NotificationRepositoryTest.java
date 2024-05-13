package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Notification;
import itgo.it_secondhand.domain.NotificationMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class NotificationRepositoryTest {
    @Autowired
    NotificationRepository notificationRepository;

    @Test
    public void save() throws Exception {
        // given
        List<NotificationMessage> notificationMessageList = new ArrayList<>();
        notificationMessageList.add(new NotificationMessage("deviceName1의 최저가가 인하되었습니다.", LocalDateTime.now()));
        notificationMessageList.add(new NotificationMessage("deviceName1의 최저가가 인하되었습니다.", LocalDateTime.now()));
        notificationMessageList.add(new NotificationMessage("deviceName2의 최저가가 인하되었습니다.", LocalDateTime.now()));

        Notification notification = Notification.builder().memberId(1L).messages(notificationMessageList).build();

        // when
        notificationRepository.save(notification);

        // then

    }

    @Test
    public void checkList() throws Exception {
        // given
        Long memberId = 1L;

        // when
        Notification notifications = notificationRepository.findByMemberId(memberId);

        // then
        assertEquals(notifications.getMessages().size(), 3, "3개가 저장돼야해요!");

    }

    @Test
    public void delete() throws Exception {
        // given
        Notification notification = notificationRepository.findByMemberId(1L);

        // when
        notificationRepository.deleteById(notification.getId());

        // then
        notificationRepository.findAll();

    }



}