package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.mocks.EnvironmentMocks;
import com.springboilerplate.springboilerplate.mocks.MapperMocks;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.mapper.UserDtoMapper;
import com.springboilerplate.springboilerplate.mocks.RoleMocks;
import com.springboilerplate.springboilerplate.mocks.UserMocks;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import com.springboilerplate.springboilerplate.stubs.TestStubs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    //dependencies
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserDtoMapper userDtoMapper;

    //system under test.
    private UserService userService;

    //Mocks.
    private MapperMocks mapperMocks = new MapperMocks();
    private RoleMocks roleMocks = new RoleMocks();
    private UserMocks userMocks = new UserMocks();
    private EnvironmentMocks environmentMocks = new EnvironmentMocks();

    @Before
    public void setUp() throws Exception {
        mapperMocks.initMocks(userDtoMapper);
        roleMocks.initMocks(roleRepository);
        userMocks.initMocks(userRepository);
        userService = new UserServiceImpl(roleRepository, userRepository, userDtoMapper);
    }

    @Test
    public void saveUserShouldSave() throws Exception {
        UserDto userDto = TestStubs.generateUserDto();
        User user = userService.saveUser(userDto, RoleType.DRIVER);

        assertThat(user).isNotNull();
    }
    //68a02f01cb8f188edc20d7ff42dabcde587e94bc685e1cef862a9af9ae0bad53d586ee16f92f1b70

}