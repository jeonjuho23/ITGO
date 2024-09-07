package itgo.it_secondhand.api;

import itgo.it_secondhand.repository.CategoryRepository;
import itgo.it_secondhand.service.device.DTO.*;
import itgo.it_secondhand.service.device.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceRestController.class)
class DeviceRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeviceServiceImpl deviceService;
    @MockBean
    CategoryRepository categoryRepository;

    private StringBuilder sb;

    @BeforeEach
    void setUp() {
        sb = new StringBuilder("/api/v2/devices");
    }

    @Test
    public void findDeviceList() throws Exception {
        //given
        when(deviceService.findDeviceList(any(FindDeviceListReqDTO.class)))
                .thenReturn(mock(FindDeviceListResDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(deviceService, times(1))
                .findDeviceList(any(FindDeviceListReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findDeviceListByCategory() throws Exception {
        //given
        when(deviceService.findDeviceListByCategory(any(FindDeviceListByCategoryReqDTO.class)))
                .thenReturn(mock(FindDeviceListResDTO.class));

        String requestUrl = sb.append("/by/{categoryId}").toString();
        Long categoryId = 1L;
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, categoryId)
                        .params(requestParams)
                );

        //then
        verify(deviceService, times(1))
                .findDeviceListByCategory(any(FindDeviceListByCategoryReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.deviceList").isArray())
                .andDo(print());
    }


    @Test
    public void findCategory() throws Exception {
        //given
        when(categoryRepository.findAll())
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/categories").toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl));

        //then
        verify(categoryRepository, times(1))
                .findAll();
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


    @Test
    public void findMobileInfo() throws Exception {
        //given
        when(deviceService.findMobileInfo(any(FindDeviceInfoReqDTO.class)))
                .thenReturn(mock(FindDeviceInfoResDTO.class));


        String requestUrl = sb.append("/mobile/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId));

        //then
        verify(deviceService, times(1))
                .findMobileInfo(any(FindDeviceInfoReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findLaptopInfo() throws Exception {
        //given
        when(deviceService.findLaptopInfo(any(FindDeviceInfoReqDTO.class)))
                .thenReturn(mock(FindDeviceInfoResDTO.class));

        String requestUrl = sb.append("/laptop/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId));

        //then
        verify(deviceService, times(1))
                .findLaptopInfo(any(FindDeviceInfoReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


}