package itgo.it_secondhand.api;

import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.search.DTO.RecentSearchReqDTO;
import itgo.it_secondhand.service.search.DTO.RecentSearchResDTO;
import itgo.it_secondhand.service.search.DTO.SearchReqDTO;
import itgo.it_secondhand.service.search.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        requestParams.add("memberId", "1");
        requestParams.add("keyword", "keyword");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(searchService, times(1))
                .keywordSearch(any(SearchReqDTO.class));
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
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(searchService, times(1))
                .recentSearches(any(RecentSearchReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


}