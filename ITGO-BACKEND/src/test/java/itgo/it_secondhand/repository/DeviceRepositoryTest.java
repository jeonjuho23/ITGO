package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DeviceRepositoryTest {
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static Device device;
    private static Category category;

    @BeforeEach
    void setUp(){
        category = Category.createCategory("manufacturer", "deviceType");
        categoryRepository.save(category);
        device = Device.createDevice("deviceName", 1000, category, LocalDateTime.of(LocalDate.of(2024, Month.JULY, 1), LocalTime.of(0, 0)), "detailId");
        deviceRepository.save(device);
    }

    @Test
    public void findSliceBy() throws Exception {
        //given
        Pageable pageable = createPageable();

        //when
        Slice<Device> response = deviceRepository.findSliceBy(pageable);

        //then
        assertThat(response.getContent().get(0).getId()).isEqualTo(device.getId());
    }

    @Test
    public void findSliceByCategory_Id() throws Exception {
        //given
        Pageable pageable = createPageable();
        Long categoryId = category.getId();

        //when
        Slice<Device> response = deviceRepository.findSliceByCategory_Id(pageable, categoryId);

        //then
        assertThat(response.getContent().get(0).getId()).isEqualTo(device.getId());
    }

    @Test
    public void searchDeviceByDeviceName() throws Exception {
        //given
        String keyword = device.getDeviceName();

        //when
        List<Device> response = deviceRepository.searchDeviceByDeviceName(keyword);

        //then
        assertThat(response.get(0).getId()).isEqualTo(device.getId());
    }

    private static PageRequest createPageable() {
        return PageRequest.of(0, 10);
    }

}