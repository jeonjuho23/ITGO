package itgo.it_secondhand.service.device.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FindDeviceListResDTO {
    private List<FindDeviceDTO> deviceList;
    private Boolean hasNext;
}
