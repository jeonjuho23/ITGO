package itgo.it_secondhand.service.notification;

import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;

public interface NotificationService {

    // 리팩터링 필요..
    /*
       현재는 모든 리스트를 한번에 가져오는 것이지만
       데이터가 늘어남에 따라서 부담이 커질 수 있으므로
       Slice 형태로 받을 수 있도록
       DB 모델링부터 해야할 필요가 있어 보임.
     */
    public CheckNotificationResDTO findNotificationList(CheckNotificationReqDTO checkNotificationReqDTO);
    public void deleteNotification(ManageNotificationReqDTO manageNotificationReqDTO);
    public void deleteAllNotification(ManageNotificationReqDTO manageNotificationReqDTO);
}
