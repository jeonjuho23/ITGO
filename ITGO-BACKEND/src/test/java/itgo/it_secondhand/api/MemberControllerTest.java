package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.api.DTO.Member.FetchMemberProfileResponseDTO;
import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberServiceImpl memberService;

    @Autowired
    ObjectMapper objectMapper;
    StringBuilder sb;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/members");
    }

    @Test
    public void registerUser() throws Exception {
        //given
        when(memberService.createMember(any(MemberDTO.class)))
                .thenReturn(mock(Member.class));

        String requestUrl = sb.append("/signup").toString();
        String requestBody = objectMapper.writeValueAsString(mock(MemberDTO.class));

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        //then
        verify(memberService, times(1))
                .createMember(any(MemberDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNumber())
                .andDo(print());
    }


    @Test
    public void myProfile() throws Exception {
        //given
        when(memberService.getByCredentials(anyLong()))
                .thenReturn(mock(FetchMemberProfileResponseDTO.class));

        String requestUrl = sb.append("/{memberId}/profiles").toString();
        Long memberId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId));

        //then
        verify(memberService, times(1))
                .getByCredentials(anyLong());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void updateProfile() throws Exception {
        //given
        when(memberService.updateMember(any(MemberDTO.class), anyLong()))
                .thenReturn(mock(FetchMemberProfileResponseDTO.class));

        String requestUrl = sb.append("/{memberId}/profiles").toString();
        String requestBody = objectMapper.writeValueAsString(mock(MemberDTO.class));
        Long memberId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(patch(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        verify(memberService, times(1))
                .updateMember(any(MemberDTO.class), anyLong());
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


}