package com.springboilerplate.springboilerplate.service;

import com.springboilerplate.springboilerplate.constants.EnvironmentConstants;
import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.mapper.UserDtoMapper;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.RoleRepository;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
//    @Value("${password.key}")
    private String passwordKey = EnvironmentConstants.PASSWORD_KEY;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public User saveUser(UserDto userDto, RoleType roleType) {
        User user = userDtoMapper.toUser(userDto);
        encodeAndSetUserPassword(user);
        return setUserRole(user, roleType);
    }

    private void encodeAndSetUserPassword(User user){
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(passwordKey);
        String encoded = encoder.encode(user.getPassword().trim());
//        System.out.println(encoded);
        user.setPassword(encoded);//167ef05b7a218b77f657ecc36b869b64dae7ea189535e1d40518feb60401031fb03e9d22e890f1d3
    }

    private User setUserRole(User user, RoleType roleType){
        Optional<Role> optionalRole = roleRepository.findByName(roleType.name());
        optionalRole.ifPresent(user::setRole);
        return userRepository.saveAndFlush(user);
    }
}
