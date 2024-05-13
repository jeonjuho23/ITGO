package itgo.it_secondhand.service.like.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceResDTO {
    private Long id;
    private String deviceName;
}
