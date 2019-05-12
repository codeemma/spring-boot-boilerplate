package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.SpringBoilerplateApplication;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.security.AccountCredentials;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
@SpringBootTest(classes = SpringBoilerplateApplication.class)
@WebAppConfiguration
@Transactional
public class OrderControllerTest {
    @Autowired
    private FilterChainProxy springSecurityFilter;
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private TokenAuthenticationService tokenAuthenticationService;
//    @Autowired
//    private


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private String clientHeader;
    private String driverHeader;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    @Rollback
    public void setup() throws Exception {
//        userRepository.saveAndFlush(new User("User", "Patrick", "68a02f01cb8f188edc20d7ff42dabcde587e94bc685e1cef862a9af9ae0bad53d586ee16f92f1b70",
//                "user@yahoo.com"));

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilter, "/*").build();
        AccountCredentials credentials = new AccountCredentials("client@email.com", "password");

        clientHeader = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn()
                .getResponse()
                .getHeader("AUTHORIZATION");
        credentials.setEmail("driver@email.com");
        driverHeader = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn()
                .getResponse()
                .getHeader("AUTHORIZATION");
    }

    @Test
    public void saveOrder() throws Exception {
        mockMvc.perform(post("/orders")
                .header("AUTHORIZATION",clientHeader)
                .content(JsonUtils.json(TestStubs.generateOrder(), mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk()).andDo(print());
    }

//    @Test
//    public void saveOrdershouldfailForDriver() throws Exception {
//        mockMvc.perform(post("/orders")
//                .header("AUTHORIZATION",driverHeader)
//                .content(JsonUtils.json(TestStubs.generateOrder(), mappingJackson2HttpMessageConverter))
//                .contentType(contentType))
//                .andExpect(status().isForbidden());
//    }
}