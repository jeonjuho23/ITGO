package itgo.it_secondhand.service.notification;

import itgo.it_secondhand.domain.Notification;
import itgo.it_secondhand.domain.NotificationMessage;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.NotificationRepository;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Mock
    NotificationRepository notificationRepository;


    @Test
    public void findNotificationList() throws Exception {
        //given
        NotificationMessage notificationMessage = new NotificationMessage("message", null);
        Notification notification = Notification.builder()
                .messages(new ArrayList<>(List.of(notificationMessage))).build();
        when(notificationRepository.findByMemberId(anyLong()))
                .thenReturn(notification);


        CheckNotificationReqDTO request = new CheckNotificationReqDTO(1L, 0, 10);

        //when
        CheckNotificationResDTO response = notificationService.findNotificationList(request);

        //then
        assertThat(response.getNotificationMessageList().get(0)).isEqualTo(notificationMessage.getMessage());
    }


    @Test
    public void findNotificationListThrowNoNotificationListException() throws Exception {
        //given
        when(notificationRepository.findByMemberId(anyLong()))
                .thenReturn(mock(Notification.class));


        CheckNotificationReqDTO request = new CheckNotificationReqDTO(1L, 0, 10);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            notificationService.findNotificationList(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.NO_NOTIFICATION_LIST);
    }


    @Test
    public void deleteNotification() throws Exception {
        //given
        NotificationMessage notificationMessage = new NotificationMessage("message", null);
        Notification notification = Notification.builder()
                .messages(new ArrayList<>(List.of(notificationMessage))).build();
        int messageSize = notification.getMessages().size();
        when(notificationRepository.findByMemberId(anyLong()))
                .thenReturn(notification);


        ManageNotificationReqDTO request = new ManageNotificationReqDTO(1L, 0);

        //when
        notificationService.deleteNotification(request);

        //then
        assertThat(notification.getMessages().size()).isLessThan(messageSize);
    }

}