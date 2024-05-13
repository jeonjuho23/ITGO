package itgo.it_secondhand.service.notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CheckNotificationReqDTO {
    private Long memberId;
    private int page;
    private int size;
}
