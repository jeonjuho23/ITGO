package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.service.post.DTO.*;
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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostRestController.class)
class PostRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ScrapingPostServiceImpl scrapingPostService;

    StringBuilder sb;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/posts");
    }

    @Test
    public void findPostList() throws Exception {
        //given
        when(scrapingPostService.findALlScrapingPostList(any(FindPostReqDTO.class)))
                .thenReturn(mock(FindPostResDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(scrapingPostService, times(1))
                .findALlScrapingPostList(any(FindPostReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void viewPost() throws Exception {
        //given
        when(scrapingPostService.viewScrapingPost(any(PostViewReqDTO.class)))
                .thenReturn(mock(ScrapedPostViewResDTO.class));

        String requestUrl = sb.append("/{postId}").toString();
        Long postId = 1L;
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, postId)
                        .params(requestParams)
                );

        //then
        verify(scrapingPostService, times(1))
                .viewScrapingPost(any(PostViewReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findPostByCategory() throws Exception {
        //given
        when(scrapingPostService.findScrapingPostListByCategory(any(FindPostByCategoryReqDTO.class)))
                .thenReturn(mock(FindPostResDTO.class));

        String requestUrl = sb.append("/category/{categoryId}").toString();
        Long categoryId = 1L;
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, categoryId)
                        .params(requestParams)
                );

        //then
        verify(scrapingPostService, times(1))
                .findScrapingPostListByCategory(any(FindPostByCategoryReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void findPostByLocation() throws Exception {
        //given
        when(scrapingPostService.findScrapingPostListByLocation(any(FindPostByLocationReqDTO.class)))
                .thenReturn(mock(FindPostResDTO.class));

        String requestUrl = sb.append("/location/{city}").toString();
        String city = "city";
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, city)
                        .params(requestParams)
                );

        //then
        verify(scrapingPostService, times(1))
                .findScrapingPostListByLocation(any(FindPostByLocationReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


}