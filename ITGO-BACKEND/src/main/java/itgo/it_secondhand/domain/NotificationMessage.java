package itgo.it_secondhand.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationMessage {
    private String message;
    private LocalDateTime time;
}
