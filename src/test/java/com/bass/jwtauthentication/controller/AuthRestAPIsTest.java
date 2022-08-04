package com.bass.jwtauthentication.controller;


import com.bass.jwtauthentication.model.User;
import com.bass.jwtauthentication.repository.RoleRepository;
import com.bass.jwtauthentication.repository.UserRepository;
import com.bass.jwtauthentication.security.jwt.JwtAuthEntryPoint;
import com.bass.jwtauthentication.security.jwt.JwtProvider;
import com.bass.jwtauthentication.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthRestAPIs.class)
public class AuthRestAPIsTest {

    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    UserRepository userRepository;
    @MockBean
    RoleRepository roleRepository;
    @MockBean
    PasswordEncoder encoder;
    @MockBean
    JwtProvider jwtProvider;
    @MockBean
    AuthRestAPIs authRestAPIs;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    JwtAuthEntryPoint jwtAuthEntryPoint;
    ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private TestRestTemplate template;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void authenticateUser() throws Exception {

//        String body ="{\"username\":\"nes\", \"password\":\"password\"}";
        User user = new User("zed", "zed",
                "zed@mail.com", "password");
        String jsonRequest = om.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/auth/signin")
                .characterEncoding("utf-8")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .content(jsonRequest)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        MvcResult result = mvc.perform(request).andReturn();

        Assert.assertNotNull(user);
        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void registerUser() throws Exception {

        User user = new User("zed", "zed",
                "zed@mail.com", "password");
        String jsonRequest = om.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/auth/signup")
                .characterEncoding("utf-8")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .content(jsonRequest)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        MvcResult result = mvc.perform(request).andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());

    }
}