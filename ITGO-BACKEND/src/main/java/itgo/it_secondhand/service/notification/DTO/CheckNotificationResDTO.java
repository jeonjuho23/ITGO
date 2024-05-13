package itgo.it_secondhand.service.notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckNotificationResDTO {
    private List<String> notificationMessageList;
}
