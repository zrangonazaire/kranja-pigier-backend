package com.pigierbackend.utilisateur;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigierbackend.jwtutility.JwtUtil;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UtilisateurDetailService utilisateurDetailService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testCreateAuthenticationToken_Success() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user");
        request.setPassword("password");
        
        UserDetails userDetails = mock(UserDetails.class);
        String token = "mockToken";

        when(utilisateurDetailService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(token);

        mockMvc.perform(post("/auth/authentification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    public void testCreateAuthenticationToken_Failure() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user");
        request.setPassword("wrongPassword");

        doThrow(new RuntimeException("Authentication failed"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        mockMvc.perform(post("/auth/authentification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
