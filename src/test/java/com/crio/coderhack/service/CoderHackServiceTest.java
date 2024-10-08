package com.crio.coderhack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.crio.coderhack.dto.UserDto;
import com.crio.coderhack.exception.UserDontExistException;
import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.exchange.GetCoderHackResponse;
import com.crio.coderhack.models.User;
import com.crio.coderhack.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
@SpringBootTest

public class CoderHackServiceTest {

    @InjectMocks
    private CoderHackServiceImpl coderHackServiceImplMock;

    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private MongoTemplate mongoTemplate;


    private User user;
    private CoderHackResponse coderHackResponse;
    private String userId = "id1";
    private UserDto userDto;
    private GetCoderHackResponse getCoderHackResponse;

        @BeforeEach
    public void initializeMemeObjects() throws IOException {
     
      MockitoAnnotations.initMocks(this);
      user = new User("id1", "Raj", 0, new HashSet<>());
      coderHackResponse = new CoderHackResponse("id1");
      userDto = new UserDto();
      getCoderHackResponse = new GetCoderHackResponse();  

    }


    @Test
    void testFetchUserByIdService() {

    }

    @Test
    void testFetchUsersService() throws UserDontExistException {
        List<User> userList = List.of(user);
        userDto.setUserId(user.getUserId());
        userDto.setScore(user.getScore());
        userDto.setUserName(user.getUserName());
        userDto.setBadges(user.getBadges());
        when(userRepositoryMock.findAll()).thenReturn(userList);
        assertEquals(1, userList.size());

       List<UserDto> userDtoList =  List.of(userDto);
       //getCoderHackResponse.setUsers(userDtoList);
       getCoderHackResponse = coderHackServiceImplMock.fetchUsersService();
       assertEquals(1, getCoderHackResponse.getUsers().size());
       verify(userRepositoryMock,times(1)).findAll();

    }

    @Test
    void testRegisterUserService() {
       //when( mongoTemplateMock.save(user,"userTest")).thenReturn(user);
       User savedUserTest = mongoTemplate.save(user, "userTest");
       savedUserTest.getUserId();
       assertEquals(userId, savedUserTest.getUserId());

    }

    @Test
    void testRemoveUserService() {

        when(userRepositoryMock.findById("id1")).thenReturn(Optional.of(user));
        coderHackServiceImplMock.removeUserService("id1");
        verify(userRepositoryMock,times(1)).deleteById("id1");
    }

    @Test
    void testUpdateUserByIdService() {

        when(userRepositoryMock.findById("id1")).thenReturn(Optional.of(user));
        //coderHackServiceImplMock.fetchUserByIdService("id1");
        userDto.setUserId(user.getUserId());
        userDto.setScore(user.getScore());
        userDto.setUserName(user.getUserName());
        userDto.setBadges(user.getBadges());
        userDto = coderHackServiceImplMock.fetchUserByIdService("id1");
        assertEquals("id1", userDto.getUserId());
        verify(userRepositoryMock,times(1)).findById("id1");

    }
}
