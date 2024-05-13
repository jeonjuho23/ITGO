package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Slice<Device> findSliceBy(Pageable pageable);

    Slice<Device> findSliceByCategory_Id(Pageable pageable, Long category_id);

    @Query(value = "select d from Device d " +
            "where lower(function('replace',d.deviceName,' ','')) like lower(concat('%',:keyword,'%')) ")
    List<Device> searchDeviceByDeviceName(String keyword);
}
