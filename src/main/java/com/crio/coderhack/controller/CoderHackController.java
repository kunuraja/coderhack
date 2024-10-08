package com.crio.coderhack.controller;

import static  com.crio.coderhack.constants.CoderHackConstants.USER_DOESNT_EXIST;

import java.util.HashSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crio.coderhack.dto.UserDto;
import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.exchange.GetCoderHackResponse;
import com.crio.coderhack.exchange.UserRequest;
import com.crio.coderhack.models.User;
import com.crio.coderhack.service.CoderHackService;

@RestController
public class CoderHackController {


    private CoderHackService coderHackService;
    //private UserRepository userRepository;
    //private ModelMapper modelMapper;



public CoderHackController(CoderHackService coderHackService
//UserRepository userRepository
    //ModelMapper modelMapper
) {
        this.coderHackService = coderHackService;
        //this.modelMapper = modelMapper;
       // this.userRepository = userRepository;
    }

@GetMapping("/welcome")
public String welcomeCodeHackers(){
    return "welcome code hackers";
} 

@GetMapping("/users")
public ResponseEntity<?> fetchUsers(){
    try {
        GetCoderHackResponse getCoderHackResponse = coderHackService.fetchUsersService();
        return ResponseEntity.status(HttpStatus.OK).body(getCoderHackResponse);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_DOESNT_EXIST);
    }
} 


@GetMapping("/users/{userId}")
public ResponseEntity<?> fetchUserById(@PathVariable String userId){
    try {
        UserDto userDto = coderHackService.fetchUserByIdService(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    } catch (Exception e) {
        // TODO: handle exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_DOESNT_EXIST);
    }
    
} 

@PostMapping("/users")    
public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){

    //User user = modelMapper.map(userRequest, User.class);
    User user = new User();
    user.setUserName(userRequest.getUserName());
    user.setScore(0);
    user.setBadges(new HashSet<>());

    try {

        CoderHackResponse coderHackResponse = coderHackService.registerUserService(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(coderHackResponse);
        
    } catch (Exception e) {
        // TODO: handle exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_DOESNT_EXIST);
    }
    


}

@PutMapping("/users/{userId}")
public ResponseEntity<?> updateUserById(@PathVariable String userId, @RequestBody UserRequest userRequest ){
    User user = new User();
    user.setUserName(userRequest.getUserName());
    user.setScore(userRequest.getScore());
    user.setBadges(userRequest.getBadges());

    try {

        CoderHackResponse coderHackResponse = coderHackService.updateUserByIdService(userId,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(coderHackResponse);
        
    } catch (Exception e) {
        // TODO: handle exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_DOESNT_EXIST);
    }

    
} 

@DeleteMapping("/users/{userId}")
public ResponseEntity<?> removeUser(@PathVariable String userId){
  
    try {
        coderHackService.removeUserService(userId);
        return ResponseEntity.status(HttpStatus.OK).body("user Id " + userId + " removed successfully");
        
    } catch (Exception e) {
        // TODO: handle exception
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USER_DOESNT_EXIST);
    }
    
}

    
}
