package itgo.it_secondhand.api;

import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.CategoryRepository;
import itgo.it_secondhand.service.device.DTO.*;
import itgo.it_secondhand.service.device.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static itgo.it_secondhand.api.ControllerTestUtil.checkResponseDataThrowException;
import static org.assertj.core.api.Assertions.assertThat;
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

    StringBuilder sb;
    private static final String DEVICE_URL = "/api/v2/devices";

    @BeforeEach
    void setUp() {
        sb = new StringBuilder();
        sb.append(DEVICE_URL);
    }

    @Test
    public void findDeviceList() throws Exception {
        //given
        FindDeviceListResDTO resDTO = FindDeviceListResDTO.builder()
                .deviceList(new ArrayList<>(List.of(mock(FindDeviceDTO.class))))
                .hasNext(false)
                .build();
        when(deviceService.findDeviceList(any(FindDeviceListReqDTO.class)))
                .thenReturn(resDTO);

        String requestUrl = sb.toString();

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("page", "0");
        request.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(request)
                );

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.deviceList").isNotEmpty())
                .andExpect(jsonPath("data.hasNext").value(resDTO.getHasNext()))
                .andDo(print());
    }

    @Test
    public void findDeviceListThrowPageNotFoundException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.PAGE_NOT_FOUND;
        when(deviceService.findDeviceList(any(FindDeviceListReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.toString();

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("page", "0");
        request.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(request)
                );

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void findDeviceListByCategory() throws Exception {
        //given
        List<FindDeviceDTO> deviceList = new ArrayList<>(List.of(mock(FindDeviceDTO.class)));
        boolean hasNext = false;
        FindDeviceListResDTO resDTO = FindDeviceListResDTO.builder()
                .deviceList(deviceList)
                .hasNext(hasNext)
                .build();
        when(deviceService.findDeviceListByCategory(any(FindDeviceListByCategoryReqDTO.class)))
                .thenReturn(resDTO);

        String requestUrl = sb.append("/by/{categoryId}").toString();
        Long categoryId = 1L;

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(requestParams));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.deviceList").isNotEmpty())
                .andExpect(jsonPath("data.hasNext").value(hasNext))
                .andDo(print());
    }


    @Test
    public void findDeviceListByCategoryThrowPageNotFoundException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.PAGE_NOT_FOUND;

        when(deviceService.findDeviceListByCategory(any(FindDeviceListByCategoryReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append("/by/{categoryId}").toString();
        Long categoryId = 1L;

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(requestParams));


        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void findCategory() throws Exception {
        //given
        when(categoryRepository.findAll())
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/categories").toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


    @Test
    public void findMobileInfo() throws Exception {
        //given
        FindDeviceInfoResDTO<MobileInfo> resDTO = FindDeviceInfoResDTO.<MobileInfo>builder()
                .info(mock(MobileInfo.class)).build();
        when(deviceService.findMobileInfo(any(FindDeviceInfoReqDTO.class)))
                .thenReturn(resDTO);


        String requestUrl = sb.append("/mobile/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.info").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findMobileInfoThrowDeviceNotFound() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.DEVICE_NOT_FOUND;
        when(deviceService.findMobileInfo(any(FindDeviceInfoReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));


        String requestUrl = sb.append("/mobile/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void findLaptopInfo() throws Exception {
        //given
        FindDeviceInfoResDTO<LaptopInfo> resDTO = FindDeviceInfoResDTO.<LaptopInfo>builder()
                .info(mock(LaptopInfo.class)).build();
        when(deviceService.findLaptopInfo(any(FindDeviceInfoReqDTO.class)))
                .thenReturn(resDTO);


        String requestUrl = sb.append("/laptop/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.info").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findLaptopInfoThrowDeviceNotFound() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.DEVICE_NOT_FOUND;
        when(deviceService.findLaptopInfo(any(FindDeviceInfoReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));


        String requestUrl = sb.append("/laptop/{detailId}").toString();
        Long detailId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, detailId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


}