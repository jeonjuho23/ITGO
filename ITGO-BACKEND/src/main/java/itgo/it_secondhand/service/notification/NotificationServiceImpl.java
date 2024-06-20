package itgo.it_secondhand.service.notification;

import itgo.it_secondhand.domain.Notification;
import itgo.it_secondhand.domain.NotificationMessage;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.NotificationRepository;
import itgo.it_secondhand.service.notification.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    @Override
    public CheckNotificationResDTO findNotificationList(CheckNotificationReqDTO checkNotificationReqDTO) {

        Notification notification = notificationRepository.findByMemberId(checkNotificationReqDTO.getMemberId());

        List<NotificationMessage> notificationMessageList = notification.getMessages();

        if (notificationMessageList.isEmpty()) throw new RestApiException(CustomExceptionCode.NO_NOTIFICATION_LIST);

        List<String> messages = new ArrayList<>();
        for(NotificationMessage notificationMessage : notificationMessageList){
            messages.add(notificationMessage.getMessage());
        }

        return new CheckNotificationResDTO(messages);
    }

    @Override
    public void deleteNotification(ManageNotificationReqDTO manageNotificationReqDTO) {

        Notification notification = notificationRepository.findByMemberId(manageNotificationReqDTO.getMemberId());
        notification.getMessages().remove(manageNotificationReqDTO.getMessageIndex());

        notificationRepository.save(notification);
    }

    @Override
    public void deleteAllNotification(ManageNotificationReqDTO manageNotificationReqDTO) {
        notificationRepository.deleteByMemberId(manageNotificationReqDTO.getMemberId());
    }
}
