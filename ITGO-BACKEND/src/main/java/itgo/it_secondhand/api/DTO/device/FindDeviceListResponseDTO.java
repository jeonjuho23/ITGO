package itgo.it_secondhand.api.DTO.device;

import itgo.it_secondhand.service.device.DTO.FindDeviceDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindDeviceListResponseDTO {
    private List<FindDeviceDTO> deviceList;
    private Boolean hasNext;
}
