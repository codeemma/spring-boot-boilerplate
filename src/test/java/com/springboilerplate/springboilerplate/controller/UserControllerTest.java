package com.springboilerplate.springboilerplate.controller;

import com.springboilerplate.springboilerplate.SpringBoilerplateApplication;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.security.AccountCredentials;
import com.springboilerplate.springboilerplate.security.TokenAuthenticationService;
import com.springboilerplate.springboilerplate.service.UserService;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.nio.charset.Charset;
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
public class UserControllerTest {

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
        mockMvc.perform(post("/users/register")
                .content(JsonUtils.json(TestStubs.generateUserDto(), mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void loginUser() throws Exception {
        AccountCredentials credentials = new AccountCredentials("driver@email.com", "password");
        String authHeader = mockMvc.perform(get("/login")
                .content(JsonUtils.json(credentials, mappingJackson2HttpMessageConverter))
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn()
                .getResponse()
                .getHeader("AUTHORIZATION");
        assertNotNull(authHeader);
    }

}