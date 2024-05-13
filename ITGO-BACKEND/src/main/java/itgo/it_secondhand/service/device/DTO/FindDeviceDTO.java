package itgo.it_secondhand.service.device.DTO;

import itgo.it_secondhand.domain.Device;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindDeviceDTO {
    private Long id;
    private String deviceName;
    private String manufacturer;
    private int launchPrice;
    private LocalDateTime releaseDate;
    private String detailId;
    private String image;

    protected FindDeviceDTO(){}

    public FindDeviceDTO(Device device, String image){
        this.id = device.getId();
        this.deviceName = device.getDeviceName();
        this.manufacturer = device.getCategory().getManufacturer();
        this.launchPrice = device.getLaunchPrice();
        this.releaseDate = device.getReleaseDate();
        this.detailId = device.getDetailId();
        this.image = image;
    }


}
