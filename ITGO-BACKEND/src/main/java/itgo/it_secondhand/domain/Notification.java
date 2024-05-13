package itgo.it_secondhand.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "notification")
@Builder
@Data
public class Notification {
    @Id
    private String id;
    private Long memberId;
    private List<NotificationMessage> messages;
}
