package com.gold.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gold.app.Application;
import com.gold.model.RoleEntity;
import com.gold.model.UserEntity;
import com.gold.security.jwt.JwtTokenUtil;
import com.gold.security.jwt.JwtUser;
import com.gold.security.jwt.JwtUserFactory;
import com.gold.security.service.JwtAuthenticationRequest;
import com.gold.security.service.JwtUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuthenticationRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    private RoleEntity role;
    private UserEntity user;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithAnonymousUser
    public void successfulAuthenticationWithAnonymousUser() throws Exception {

        JwtAuthenticationRequest jwtAuthenticationRequest =
                new JwtAuthenticationRequest("admin", "aadmin");

        mvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void successfulRefreshTokenWithUserRole() throws Exception {
        role = RoleEntity.ROLE_ADMIN;
        user = UserEntity.builder()
                .username("username")
                .roles(Collections.singleton(role))
                .build();

        JwtUser jwtUser = JwtUserFactory.create(user);

        when(jwtTokenUtil.getUsernameFromToken(ArgumentMatchers.any())).thenReturn(user.getUsername());
        when(jwtUserDetailsService.loadUserByUsername(ArgumentMatchers.eq(user.getUsername()))).thenReturn(jwtUser);
        when(jwtTokenUtil.canTokenBeRefreshed(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);

        mvc.perform(get("/api/auth/refresh")
            .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void successfulRefreshTokenWithAdminRole() throws Exception {
        role = RoleEntity.ROLE_ADMIN;
        user = UserEntity.builder()
                .username("admin")
                .roles(Collections.singleton(role))
                .build();

        JwtUser jwtUser = JwtUserFactory.create(user);

        when(jwtTokenUtil.getUsernameFromToken(ArgumentMatchers.any())).thenReturn(user.getUsername());
        when(jwtUserDetailsService.loadUserByUsername(ArgumentMatchers.eq(user.getUsername()))).thenReturn(jwtUser);
        when(jwtTokenUtil.canTokenBeRefreshed(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);

        mvc.perform(get("/api/auth/refresh")
            .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTU1ODQzODE0NiwiaWF0IjoxNTU4NDM0NTQ2fQ.AmLcsN-535vM2zADxaw8MO7NqumZM6pNXs6Un6Fh6N-vN7CT4FuGV0CCUbAGLZsPrZMTDw3xwrhKgKwLAU_org"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    public void shouldGetUnauthorizedWithAnonymousUser() throws Exception {

        mvc.perform(get("/api/auth/refresh"))
            .andExpect(status().isUnauthorized());
    }

}

