package itgo.it_secondhand.service.device.DTO;

import itgo.it_secondhand.domain.MobileInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindDeviceInfoResDTO<T> {
    private T info;
}
