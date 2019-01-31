package com.eurodyn.qlack.test.services.aaa;

import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserTestService {

    private final UserService userService;

    @Autowired
    public UserTestService(UserService userServiceService) {
        this.userService = userServiceService;
    }

    public void createUser(){
        UserDTO user = new UserDTO();
        user.setUsername("imousmoutis");
        user.setPassword("imousmoutis");
        user.setStatus((byte) 1);
        user.setUserAttributes(new HashSet<>());
        userService.createUser(user);
    }

}
