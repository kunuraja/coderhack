package com.crio.coderhack.service;

import static com.crio.coderhack.constants.CoderHackConstants.USER_DOESNT_EXIST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.crio.coderhack.dto.UserDto;
import com.crio.coderhack.exception.UserDontExistException;
import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.exchange.GetCoderHackResponse;
import com.crio.coderhack.models.User;
import com.crio.coderhack.repository.UserRepository;

@Service
public class CoderHackServiceImpl implements CoderHackService {

    private UserRepository userRepository;
    // private ModelMapper modelMapper;
    private MongoTemplate mongoTemplate;
    private CoderHackResponse coderHackResponse;

    public CoderHackServiceImpl(UserRepository userRepository,
            // ModelMapper modelMapper,
            MongoTemplate mongoTemplate
    // CoderHackResponse coderHackResponse
    ) {
        this.userRepository = userRepository;
        // this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
        // this.coderHackResponse = coderHackResponse;
    }

    @Override
    public CoderHackResponse registerUserService(User user) {
        // TODO Auto-generated method stub
        User userReturnedAfterSave = mongoTemplate.save(user, "user");
        System.out.println(userReturnedAfterSave.toString());
        coderHackResponse = new CoderHackResponse();
        coderHackResponse.setId(userReturnedAfterSave.getUserId());
        return coderHackResponse;
    }

    @Override
    public GetCoderHackResponse fetchUsersService() throws UserDontExistException {
        // TODO Auto-generated method stub
        List<User> sortedUsers = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserDontExistException(USER_DOESNT_EXIST);}
       
        
        
        sortedUsers = users.stream().sorted(Comparator.comparing(User::getScore).reversed()).collect(Collectors.toList());
        sortedUsers.stream().forEach(System.out:: println);
        List<UserDto> userDtos = new ArrayList<>(); 
        sortedUsers.stream().forEach(t ->{
        UserDto userDto = new UserDto();  
        userDto.setUserId(t.getUserId());
        userDto.setScore(t.getScore());
        userDto.setUserName(t.getUserName());
        userDto.setBadges(t.getBadges());
        userDtos.add(userDto);
    }); 
    
    // for (User t : sortedUsers){
    //     UserDto userDto = new UserDto();
    //     userDto.setUserId(t.getUserId());
    //     userDto.setScore(t.getScore());
    //     userDto.setUserName(t.getUserName());
    //     userDto.setBadges(t.getBadges());
    //     userDtos.add(userDto);
    // }



        // sortedUsers.stream().map(t -> {
        //     userDto.setUserId(t.getUserId());
        //     userDto.setScore(t.getScore());
        //     userDto.setUserName(t.getUserName());
        //     userDto.setBadges(t.getBadges());
        //     userDtos.add(userDto);
        //     return userDto;
        // });
        //.collect(Collectors.toList());
        System.out.println(userDtos);
        userDtos.stream().forEach(System.out:: println);
        GetCoderHackResponse coderHackResponse = new GetCoderHackResponse();
        coderHackResponse.setUsers(userDtos);
        return coderHackResponse;

    }

    @Override
    public UserDto fetchUserByIdService(String userId) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(userId).orElseThrow();
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setScore(user.getScore());
        userDto.setUserName(user.getUserName());
        userDto.setBadges(user.getBadges());

        return userDto;
    }

    @Override
    public CoderHackResponse updateUserByIdService(String userId, User user) {
        Set<String> badgeSet = new HashSet<>();
        User userReturned = userRepository.findById(userId).orElseThrow();
        userReturned.setScore(user.getScore());

        if (user.getScore() < 30 && user.getScore() >= 1) {
            userReturned.getBadges().add("Code Ninja");
            // userReturned.setBadges(badgeSet);
        } else if (user.getScore() < 60 && user.getScore() >= 30) {
            // badgeSet.add("Code Champ");
            userReturned.getBadges().add("Code Champ");
            // userReturned.setBadges(badgeSet);
        } else if (user.getScore() <= 100 && user.getScore() >= 60) {
            // badgeSet.add("Code Master");
            userReturned.getBadges().add("Code Master");
            // userReturned.setBadges(badgeSet);
        }

        User userUpdated = mongoTemplate.save(userReturned);
        System.out.println("user updated :" + userUpdated);
        CoderHackResponse coderHackResponse = new CoderHackResponse();
        coderHackResponse.setId(userUpdated.getUserId());
        return coderHackResponse;
    }

    @Override
    public void removeUserService(String userId) {
        userRepository.findById(userId).orElseThrow();
        userRepository.deleteById(userId);
    }

}
