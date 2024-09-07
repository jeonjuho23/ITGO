package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    ObjectMapper objectMapper;
    StringBuilder sb;
    Long memberId;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/{memberId}/like");
        memberId = 1L;
    }

    @Test
    public void registDevice() throws Exception {
        //given
        when(deviceLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(1L);

        String requestUrl = sb.append("/devices").toString();
        String requestBody = objectMapper.writeValueAsString(1L);

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        verify(deviceLikeService, times(1))
                .regist(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNumber())
                .andDo(print());
    }


    @Test
    public void registPost() throws Exception {
        //given
        when(postLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(1L);

        String requestUrl = sb.append("/posts").toString();
        String requestBody = objectMapper.writeValueAsString(1L);

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        verify(postLikeService, times(1))
                .regist(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNumber())
                .andDo(print());
    }


    @Test
    public void registLocation() throws Exception {
        //given
        when(locationLikeService.regist(any(LikeReqDTO.class)))
                .thenReturn(1L);

        String requestUrl = sb.append("/locations").toString();
        String requestBody = objectMapper.writeValueAsString("locationId");

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        verify(locationLikeService, times(1))
                .regist(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNumber())
                .andDo(print());
    }


    @Test
    public void deleteDevice() throws Exception {
        //given
        doNothing().when(deviceLikeService)
                .delete(any(LikeReqDTO.class));

        String requestUrl = sb.append("/devices/{deviceID}").toString();
        Long deviceID = 1L;

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, deviceID));

        //then
        verify(deviceLikeService, times(1))
                .delete(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void deletePost() throws Exception {
        //given
        doNothing().when(postLikeService)
                .delete(any(LikeReqDTO.class));

        String requestUrl = sb.append("/posts/{postId}").toString();
        Long postId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, postId));

        //then
        verify(postLikeService, times(1))
                .delete(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void deleteLocation() throws Exception {
        //given
        doNothing().when(locationLikeService)
                .delete(any(LikeReqDTO.class));

        String requestUrl = sb.append("/locations/{likeId}").toString();
        Long likeId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, memberId, likeId));

        //then
        verify(locationLikeService, times(1))
                .delete(any(LikeReqDTO.class));
        action.andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void findDeviceList() throws Exception {
        //given
        when(postLikeService.checkList(anyLong()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/devices").toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId));

        //then
        verify(postLikeService, times(1))
                .checkList(anyLong());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


    @Test
    public void findLikePostList() throws Exception {
        //given
        when(scrapingPostService.findLikeScrapingPostList(any(FindPostReqDTO.class)))
                .thenReturn(mock(FindPostResDTO.class));

        String requestUrl = sb.append("/posts").toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId));

        //then
        verify(scrapingPostService, times(1))
                .findLikeScrapingPostList(any(FindPostReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findLocationList() throws Exception {
        //given
        when(locationLikeService.checkList(anyLong()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/locations").toString();

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId));

        //then
        verify(locationLikeService, times(1))
                .checkList(anyLong());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


    @Test
    public void findDeviceByKeyword() throws Exception {
        //given
        when(deviceLikeService.findByKeyword(anyString()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/devices/search").toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                );

        //then
        verify(deviceLikeService, times(1))
                .findByKeyword(anyString());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


    @Test
    public void findLocationByKeyword() throws Exception {
        //given
        when(locationLikeService.findByKeyword(anyString()))
                .thenReturn(mock(ArrayList.class));

        String requestUrl = sb.append("/locations/search").toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .params(requestParams)
                );

        //then
        verify(locationLikeService, times(1))
                .findByKeyword(anyString());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andDo(print());
    }


}