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

    @Autowired
    ObjectMapper objectMapper;

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
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
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
        String postId = objectMapper.writeValueAsString(1L);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, postId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
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
        String categoryId = objectMapper.writeValueAsString(1L);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, categoryId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
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
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, city)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    /**
     * 예외처리 테스트에 관해서..
     *
     * 컨트롤러에서 발생 가능한 예외는 서비스에서 발생된 예외가 영향을 끼치는 것만이 있다.
     * 물론 HTTP 요청에서의 문제가 발생될 수도 있지만,
     * 이를 테스트하는 것은 통합 테스트 혹은
     * HTTP 요청과 응답 확인만을 목적으로 하는 테스트가 아닐까??
     *
     * 컨트롤러의 역할은 무엇인가...?
     * => 컨트롤러는 HTTP 요청을 수신하고 받은 데이터를 service에 요청해
     *      비즈니스 로직을 수행하고 이를 통해 반환받은 데이터를
     *      HTTP 응답 데이터로 반환하는 역할을 갖는다.
     *
     * Service 에서 단위 테스트를 진행할 때에 메서드의 매개변수를 테스트하는가??
     * => 적절한 요청과 요청 데이터는 적절하지만 비즈니스 로직을 수행할 수 없는 요청
     *      이 2가지만 테스트했는데... 이는 매개변수를 테스트한 것은 아니다!!
     *      단지 비즈니스 로직을 테스트할 뿐!
     *      => 그렇다면 컨트롤러 테스트도 요청에 대한 검증은 통합 테스트 혹은
     *          HTTP 요청에 대한 또 다른 단위 테스트가 존재하는 것이 좋은 방법 아닐까??
     *          컨트롤러 단위 테스트에서는 원하는 적절한 응답 데이터가 나오는지만 확인하면 될것같다!!
     *
     * Request Parameter / Request Body 를 테스트 하는 것은 어디여야 하는가??
     * => 이것만을 테스트하는 클래스를 만드는 것이 적절하다 생각한다.
     *      =>> 이것들이 문제가 된다면 예외를 발생시키므로
     *              전역 예외처리를 확인하는 테스트가 있어야 한다!!
     *
     ***** 컨트롤러에서의 비즈니스 로직만을 테스트하자!!
     *
     * 통합 테스트는 HTTP 요청부터 모든 로직을 정상적으로 수행하고
     * 적절한 데이터를 응답받는지를 확인하는 테스트이다.
     *
     *
     */
}