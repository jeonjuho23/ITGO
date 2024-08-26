package itgo.it_secondhand.api;

import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.service.like.DeviceLikeServiceImpl;
import itgo.it_secondhand.service.like.LocationLikeServiceImpl;
import itgo.it_secondhand.service.like.PostLikeServiceImpl;
import itgo.it_secondhand.service.post.DTO.FindPostReqDTO;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.post.ScrapingPostServiceImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static itgo.it_secondhand.api.ControllerTestUtil.checkResponseDataThrowException;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeRestController.class)
class LikeRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeviceLikeServiceImpl deviceLikeService;
    @MockBean
    PostLikeServiceImpl postLikeService;
    @MockBean
    LocationLikeServiceImpl locationLikeService;
    @MockBean
    ScrapingPostServiceImpl scrapingPostService;

    private static StringBuilder sb;

    private static final String DEVICE_URL = "/devices";
    private static final String POST_URL = "/posts";
    private static final String LOCATION_URL = "/locations";

    private static final Long memberId = 1L;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/{memberId}/like");
    }


    @Test
    public void registDevice() throws Exception {
        //given
        Long registId = 1L;
        Long deviceId = 1L;

        when(deviceLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(registId);

        String requestUrl = sb.append(DEVICE_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(String.valueOf(deviceId))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.registId").value(registId))
                .andDo(print());
    }


    @Test
    public void registDeviceThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Long deviceId = 1L;

        when(deviceLikeService.regist(any(LikeReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(DEVICE_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(String.valueOf(deviceId))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void registPost() throws Exception {
        //given
        Long registId = 1L;
        Long postId = 1L;

        when(postLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(registId);

        String requestUrl = sb.append(POST_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(String.valueOf(postId))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.registId").value(registId))
                .andDo(print());
    }


    @Test
    public void registPostThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Long postId = 1L;

        when(postLikeService.regist(any(LikeReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(POST_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(String.valueOf(postId))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }



    @Test
    public void registLocation() throws Exception {
        //given
        Long registId = 1L;
        String locationId = "locationId";

        when(locationLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(registId);

        String requestUrl = sb.append(LOCATION_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(locationId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.registId").value(registId))
                .andDo(print());
    }


    @Test
    public void registLocationThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        String LocationId = "locationId";

        when(locationLikeService.regist(any(LikeReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(LOCATION_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(LocationId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void deleteDevice() throws Exception {
        //given
        Long deviceID = 1L;

        doNothing().when(deviceLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(DEVICE_URL).append("/{deviceID}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, deviceID)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void deleteDeviceThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Long deviceID = 1L;

        doThrow(new RestApiException(exceptionCode))
                .when(deviceLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(DEVICE_URL).append("/{deviceID}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, deviceID)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void deletePost() throws Exception {
        //given
        Long postId = 1L;

        doNothing().when(postLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(POST_URL).append("/{postId}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, postId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void deletePostThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Long postId = 1L;

        doThrow(new RestApiException(exceptionCode))
                .when(postLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(POST_URL).append("/{postId}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, postId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void deleteLocation() throws Exception {
        //given
        Long likeId = 1L;

        doNothing().when(locationLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(POST_URL).append("/{likeId}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, likeId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void deleteLocationThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Long likeId = 1L;

        doThrow(new RestApiException(exceptionCode))
                .when(locationLikeService).delete(any(LikeReqDTO.class));

        String requestUrl = sb.append(LOCATION_URL).append("/{likeId}").toString();

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, likeId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void findDeviceList() throws Exception {
        //given
        when(postLikeService.checkList(anyLong()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append(DEVICE_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }

    @Test
    public void findDeviceListThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;

        when(postLikeService.checkList(anyLong()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(DEVICE_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void findLikePostList() throws Exception {
        //given
        FindPostResDTO resDTO = FindPostResDTO.builder()
                .posts(new ArrayList<>()).hasNext(false).build();
        when(scrapingPostService.findLikeScrapingPostList(any(FindPostReqDTO.class)))
                .thenReturn(resDTO);

        String requestUrl = sb.append(POST_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.posts").isArray())
                .andExpect(jsonPath("data.hasNext").isBoolean())
                .andDo(print());
    }

    @Test
    public void findPostListThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;

        when(scrapingPostService.findLikeScrapingPostList(any(FindPostReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(POST_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void findLocationList() throws Exception {
        //given
        when(locationLikeService.checkList(anyLong()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append(LOCATION_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }

    @Test
    public void findLocationListThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;

        when(locationLikeService.checkList(anyLong()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(LOCATION_URL).toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void findDeviceByKeyword() throws Exception {
        //given
        when(deviceLikeService.findByKeyword(anyString()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append(DEVICE_URL).append("/search").toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }

    @Test
    public void findDeviceByKeywordThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;

        when(deviceLikeService.findByKeyword(anyString()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(DEVICE_URL).append("/search").toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void findLocationByKeyword() throws Exception {
        //given
        when(locationLikeService.findByKeyword(anyString()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append(LOCATION_URL).append("/search").toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }

    @Test
    public void findLocationByKeywordThrowServiceException() throws Exception {
        //given
        CustomExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;

        when(locationLikeService.findByKeyword(anyString()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append(LOCATION_URL).append("/search").toString();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

}