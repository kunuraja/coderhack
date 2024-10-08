package com.crio.coderhack.controller;

import java.net.URI;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.models.User;
import com.crio.coderhack.service.CoderHackService;

import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.ID;
import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.USER_ID;
import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.USER_REGISTRATION_API_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CoderHackControllerTest {

    private MockMvc mvc;

    @MockBean
    private CoderHackService coderHackServiceMock;


    @InjectMocks
    private CoderHackController coderHackControllerMock;


    @BeforeEach
    public void setup() {
    //objectMapper = new ObjectMapper();

    MockitoAnnotations.initMocks(this);

    mvc = MockMvcBuilders.standaloneSetup(coderHackControllerMock).build();
    }



    @Test
    void testFetchUserById() {

        URI uri = UriComponentsBuilder
      .fromPath(USER_REGISTRATION_API_URI + ID)
      .buildAndExpand(USER_ID)
      .toUri();

      assertEquals(USER_REGISTRATION_API_URI+"/"+USER_ID , uri.toString());

    }

    @Test
    void testFetchUsers() {

        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI)
        .build()
        .toUri();
  
        assertEquals(USER_REGISTRATION_API_URI , uri.toString());

    }

    @Test
    void testRegisterUser() {
        URI uri = UriComponentsBuilder
      .fromPath(USER_REGISTRATION_API_URI)
      .build().toUri();

      assertEquals(USER_REGISTRATION_API_URI , uri.toString());

      CoderHackResponse coderHackResponse = new CoderHackResponse("id1");
      User user = new User("id1", "user1", 0, new HashSet<>());

      when(coderHackServiceMock.registerUserService(user)).thenReturn(coderHackResponse);
     // verify(coderHackServiceMock, times(1)).registerUserService(user);

    }

    @Test
    void testRemoveUser() {

        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI + ID)
        .buildAndExpand(USER_ID)
        .toUri();
  
        assertEquals(USER_REGISTRATION_API_URI +"/"+ USER_ID , uri.toString());
  
        CoderHackResponse coderHackResponse = new CoderHackResponse("id1");
        User user = new User("id1", "user1", 0, new HashSet<>());
  
        when(coderHackServiceMock.registerUserService(user)).thenReturn(coderHackResponse);

    }

    @Test
    void testUpdateUserById() {
        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI)
        .build().toUri();
  
        assertEquals(USER_REGISTRATION_API_URI , uri.toString());
  
        CoderHackResponse coderHackResponse = new CoderHackResponse("id1");
        User user = new User("id1", "user1", 0, new HashSet<>());
  
        when(coderHackServiceMock.registerUserService(user)).thenReturn(coderHackResponse);
       // verify(coderHackServiceMock, times(1)).registerUserService(user);

    }
}
