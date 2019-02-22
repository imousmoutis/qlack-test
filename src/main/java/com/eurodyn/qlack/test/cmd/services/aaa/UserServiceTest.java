package com.eurodyn.qlack.test.cmd.services.aaa;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceTest {

    private final UserService userService;

    private final String USERNAME = "ioannis.mousmoutis";

    private final UserDTO userDTO = createUserDTO();

    @Autowired
    public UserServiceTest(UserService userServiceService) {
        this.userService = userServiceService;
    }

    public void createUser(){
        System.out.println("******************");
        System.out.println("Testing createUser method.");
        UserDTO existingUser = userService.getUserByName(USERNAME);

        if (existingUser == null){
            String userId = userService.createUser(userDTO);
            System.out.println("User with id " +userId+ " has been created.");
        } else {
            System.out.println("User " +USERNAME+ " already exists.");
        }
        System.out.println("******************");
    }

    public void updateUser(){
        System.out.println("******************");
        System.out.println("Testing updateUser method.");
        UserDTO existingUser = userService.getUserByName(USERNAME);

        existingUser.setPassword("newpassword");

        existingUser.setSuperadmin(false);
        for (UserAttributeDTO u: existingUser.getUserAttributes()){
            u.setData("updated " +u.getData());
        }
        if(existingUser.getUserAttributes().size() == 2){
            UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
            userAttributeDTO.setName("email");
            userAttributeDTO.setData("ioannis.mousmoutis@eurodyn.com");
            userAttributeDTO.setUserId(existingUser.getId());
            existingUser.getUserAttributes().add(userAttributeDTO);
        }

        userService.updateUser(existingUser, false, true);
        System.out.println("User is updated.");
        System.out.println("******************");
    }

    public void deleteUser(){
        System.out.println("******************");
        System.out.println("Testing deleteUser method.");

        UserDTO existingUser = userService.getUserByName(USERNAME);
        userService.deleteUser(existingUser.getId());

        System.out.println("User is deleted.");
        System.out.println("******************");
    }

    private void getUserById(String userId){
        System.out.println("******************");
        System.out.println("Testing getUserById method.");

        UserDTO existingUser = userService.getUserById(userId);

        System.out.println("Found user is " +existingUser.getUsername());
        System.out.println("******************");
    }

    public void getUserByName(){
        System.out.println("******************");
        System.out.println("Testing getUserByName method.");

        UserDTO existingUser = userService.getUserByName(USERNAME);

        System.out.println("Found user is " +existingUser.getUsername());

        System.out.println("******************");
        getUserById(existingUser.getId());
    }

    private List<UserAttributeDTO> createUserAttributesDTO(){
        List<UserAttributeDTO> userAttributesDTO = new ArrayList<>();

        UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
        userAttributeDTO.setName("fullName");
        userAttributeDTO.setData("Ioannis Mousmoutis");
        userAttributeDTO.setContentType("text");
        userAttributesDTO.add(userAttributeDTO);

        UserAttributeDTO userAttributeDTO2 = new UserAttributeDTO();
        userAttributeDTO2.setName("company");
        userAttributeDTO2.setData("European Dynamics");
        userAttributeDTO2.setContentType("text");
        userAttributesDTO.add(userAttributeDTO2);

        return userAttributesDTO;
    }

    private UserDTO createUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(USERNAME);
        userDTO.setPassword("thisisaverysecurepassword");
        userDTO.setStatus((byte)1);
        userDTO.setSuperadmin(true);
        userDTO.setExternal(false);
        userDTO.setUserAttributes(new HashSet<>(createUserAttributesDTO()));

        return userDTO;
    }
}
