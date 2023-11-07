package flab.gumipayments.presentation;

import flab.gumipayments.application.DuplicateException;
import flab.gumipayments.application.SignupAcceptApplication;
import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
import flab.gumipayments.domain.signup.SignupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AcceptController.class)
class AcceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupAcceptApplication signupAcceptApplication;

    private MultiValueMap<String, String> request;

    @BeforeEach
    void setUp() {
        request = new LinkedMultiValueMap<>();
        request.add("signupKey", KeyFactory.generateSignupKey());
        request.add("expireKey", LocalDateTime.now().toString());
    }

    @Test
    @WithMockUser
    @DisplayName("성공: 가입 요청 인증을 성공한다.")
    void signupAccept() throws Exception {
        Long signupId = 1L;

        when(signupAcceptApplication.accept(any())).thenReturn(signupId);

        mockMvc.perform(post("/api/accept/signup")
                        .params(request)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.signupId").value(signupId));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 기간이 만료된 인증은 실패한다.")
    void expiredAccept() throws Exception {
        when(signupAcceptApplication.accept(any())).thenThrow(new SignupAcceptTimeoutException());

        mockMvc.perform(post("/api/accept/signup")
                        .params(request)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(SignupAcceptTimeoutException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 인증할 가입 요청이 존재하지 않으면 인증에 실패한다.")
    void notExistSignup() throws Exception {
        when(signupAcceptApplication.accept(any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(post("/api/accept/signup")
                        .params(request)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(NoSuchElementException.class)
                );
    }
}