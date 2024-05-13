package itgo.it_secondhand.service.device.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindDeviceInfoReqDTO {
    private String detailId;
}
