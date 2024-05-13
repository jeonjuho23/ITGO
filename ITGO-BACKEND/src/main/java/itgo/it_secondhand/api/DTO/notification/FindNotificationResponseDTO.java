package itgo.it_secondhand.api.DTO.notification;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindNotificationResponseDTO {
    private List<String> notificationList;
}
