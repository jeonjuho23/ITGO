package itgo.it_secondhand.api.DTO.device;

import itgo.it_secondhand.domain.MobileInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindDeviceInfoResponseDTO<T> {
    private T info;
}
