package com.springboilerplate.springboilerplate.stubs;

import com.springboilerplate.springboilerplate.dto.UserDto;
import com.springboilerplate.springboilerplate.enums.RoleType;
import com.springboilerplate.springboilerplate.model.Order;
import com.springboilerplate.springboilerplate.model.Role;
import com.springboilerplate.springboilerplate.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class TestStubs {

    public static User generateUser(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com", generateRole());
    }
    public static User generateUserWithNoRole(){
        return new User("Patrick", "Emmanuel",
                "Password", "email@email.com");
    }

    public static UserDto generateUserDto(){
        return new UserDto("Patrick", "Emmanuel",
                "email@email.com","password");
    }

    public static Order generateOrder() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Order("lekki", "Ajah","0-15 tons","onions",df.parse("2019-05-11 10:00:00"));
    }

    public static Role generateRole(){
        return new Role(RoleType.DRIVER.name());
    }
    public static Optional<Role> generateOptionalRole(){
        return Optional.of(new Role(RoleType.DRIVER.name()));
    }
}
