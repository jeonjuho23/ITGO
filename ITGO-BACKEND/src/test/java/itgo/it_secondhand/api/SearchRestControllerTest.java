package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.search.DTO.*;
import itgo.it_secondhand.service.search.SearchServiceImpl;
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

@WebMvcTest(SearchRestController.class)
class SearchRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchServiceImpl searchService;

    StringBuilder sb;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2");
    }



    @Test
    public void keywordSearch() throws Exception {
        //given
        when(searchService.keywordSearch(any(SearchReqDTO.class)))
                .thenReturn(mock(FindPostResDTO.class));

        String requestUrl = sb.append("/posts/search").toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));
        requestParams.add("keyword", "keyword");

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
    public void recentSearches() throws Exception {
        //given
        when(searchService.recentSearches(any(RecentSearchReqDTO.class)))
                .thenReturn(mock(RecentSearchResDTO.class));

        String requestUrl = sb.append("/search/recent").toString();
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


}