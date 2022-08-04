package com.bass.jwtauthentication.controller;

import com.bass.jwtauthentication.message.request.LoginForm;
import com.bass.jwtauthentication.model.Order;
import com.bass.jwtauthentication.repository.OrderService;
import com.bass.jwtauthentication.repository.RoleRepository;
import com.bass.jwtauthentication.repository.UserRepository;
import com.bass.jwtauthentication.security.jwt.JwtAuthEntryPoint;
import com.bass.jwtauthentication.security.jwt.JwtProvider;
import com.bass.jwtauthentication.security.services.OrderServiceHImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderRestController.class)
public class OrderControllerTest {


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
    OrderServiceHImpl orderServiceH;
    @MockBean
    OrderService service;
    @MockBean
    LoginForm loginRequest;
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
    @MockBean
    private OrderRestController restController;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void FindAll() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/api/users/all")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        MvcResult result = mvc.perform(request).andReturn();

//        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    public void findById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/orders/1")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        MvcResult result = mvc.perform(request).andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());

    }


    @Test
    public void addOrder() throws Exception {

        Order order = new Order("test", "29/11/20", "2", 50);
        String jsonRequest = om.writeValueAsString(order);
//        when(service.save(order)).thenReturn(order);
        MvcResult mvcResult = mvc.perform(post("/api/orders")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(order);
        Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void updateOrder() throws Exception {
        String body = "{\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"test\",\n" +
                "    \"date\": \"11/1520\",\n" +
                "    \"amount\": \"7\",\n" +
                "    \"total_price\": 350,\n" +
                "    \"user\": null\n" +
                "}";

        MvcResult mvcResult = mvc.perform(put("/api/orders")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        Assert.assertNotNull(mvcResult.getRequest().getContentAsString());
    }

    @Test
    public void deleteOrderById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/orders/1")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        MvcResult result = mvc.perform(request).andReturn();

        Assert.assertNotNull(result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

/*
    @Test
    public void saveUserTest() {
        Order order = new Order("orderTest", "19/12/20", "5", 33);
        when(service.save(order)).thenReturn(order);
//        Assert.assertEquals(order,orderServiceH.saveOrder(order));
    }
  @Test
    public void authTest() throws Exception {
        String body ="{\"username\":\"nes\", \"password\":\"password\"}";

        MvcResult mvcResult = mvc.perform(post("http://localhost:8080/api/auth/signin")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);

    }

    @Test
    public void existentUserCanGetTokenAndAuthentication() throws Exception {

        String body = "{\"username\":\"nes\", \"password\":\"password\"}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/signin")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"access_token\": \"", "");
        String token = response.replace("\"}", "");

        mvc.perform(MockMvcRequestBuilders.get("/test")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
*/

}
