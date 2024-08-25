package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.api.DTO.Member.FetchMemberProfileResponseDTO;
import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.service.Member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static itgo.it_secondhand.api.ControllerTestUtil.checkResponseDataThrowException;
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

    private static StringBuilder sb;

    @Autowired
    ObjectMapper objectMapper;

    private static final CustomExceptionCode exceptionCode
            = CustomExceptionCode.INTERNAL_SERVER_ERROR;

    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/members");
    }

    @Test
    public void registerUser() throws Exception {
        //given
        Member member = Member.builder()
                .id(1L).build();
        when(memberService.createMember(any(MemberDTO.class)))
                .thenReturn(member);

        String requestBody = objectMapper.writeValueAsString(MemberDTO.builder().build());

        String requestUrl = sb.append("/signup").toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").value(member.getId()))
                .andDo(print());
    }


    @Test
    public void registerUserThrowServiceException() throws Exception {
        //given
        when(memberService.createMember(any(MemberDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestBody = objectMapper.writeValueAsString(MemberDTO.builder().build());

        String requestUrl = sb.append("/signup").toString();

        //when
        ResultActions action = mockMvc
                .perform(post(requestUrl)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
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
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void myProfileThrowServiceException() throws Exception {
        //given
        when(memberService.getByCredentials(anyLong()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.append("/{memberId}/profiles").toString();
        Long memberId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl, memberId)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void updateProfile() throws Exception {
        //given
        when(memberService.updateMember(any(MemberDTO.class), anyLong()))
                .thenReturn(mock(FetchMemberProfileResponseDTO.class));

        String requestBody = objectMapper.writeValueAsString(MemberDTO.builder().build());
        String requestUrl = sb.append("/{memberId}/profiles").toString();
        Long memberId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(patch(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void updateProfileThrowServiceException() throws Exception {
        //given
        when(memberService.updateMember(any(MemberDTO.class), anyLong()))
                .thenThrow(new RestApiException(exceptionCode));

        String requestBody = objectMapper.writeValueAsString(MemberDTO.builder().build());
        String requestUrl = sb.append("/{memberId}/profiles").toString();
        Long memberId = 1L;

        //when
        ResultActions action = mockMvc
                .perform(patch(requestUrl, memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }
}