package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeviceRepositoryTest {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private Device device;
    private Category category;

    @BeforeEach
    void setUp(){
        device = getDevice();

        category = device.getCategory();

        categoryRepository.save(category);
        deviceRepository.save(device);
    }


    @Test
    public void findSliceBy() throws Exception {
        //given
        Pageable pageable = getPageable();

        //when
        Slice<Device> response = deviceRepository.findSliceBy(pageable);

        //then
        assertThat(response.getContent().get(0).getId())
                .isEqualTo(device.getId());
    }

    @Test
    public void findSliceByCategory_Id() throws Exception {
        //given
        Pageable pageable = getPageable();
        Long categoryId = category.getId();

        //when
        Slice<Device> response = deviceRepository.findSliceByCategory_Id(pageable, categoryId);

        //then
        assertThat(response.getContent().get(0).getId())
                .isEqualTo(device.getId());
    }

    @Test
    public void searchDeviceByDeviceName() throws Exception {
        //given
        String keyword = device.getDeviceName();

        //when
        List<Device> response = deviceRepository.searchDeviceByDeviceName(keyword);

        //then
        assertThat(response.get(0).getId())
                .isEqualTo(device.getId());
    }

}