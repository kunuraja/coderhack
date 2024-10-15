package com.crio.coderhack.controller;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.crio.coderhack.dto.UserDto;
import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.exchange.GetCoderHackResponse;
import com.crio.coderhack.models.User;
import com.crio.coderhack.repository.UserRepository;
import com.crio.coderhack.service.CoderHackService;

import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.ID;
import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.USER_ID;
import static com.crio.coderhack.testconstatnts.CoderHackTestConstants.USER_REGISTRATION_API_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoderHackControllerTest {

    private MockMvc mvc;

    @MockBean
    private CoderHackService coderHackServiceMock;

    @MockBean
    private UserRepository userRepositoryMock;


    @InjectMocks
    private CoderHackController coderHackControllerMock;


    @BeforeEach
    public void setup() {
    //objectMapper = new ObjectMapper();

    MockitoAnnotations.initMocks(this);

    mvc = MockMvcBuilders.standaloneSetup(coderHackControllerMock).build();
    }



    @Test
    void testFetchUserById() throws Exception {

        URI uri = UriComponentsBuilder
      .fromPath(USER_REGISTRATION_API_URI + ID)
      .buildAndExpand(USER_ID)
      .toUri();

      assertEquals(USER_REGISTRATION_API_URI+"/"+USER_ID , uri.toString());
      User user = new User(null, "Raj1", 0, new HashSet<>());
      UserDto userDto = new UserDto("userId1", "Raj1", 65, Set.of("Code Master"));

      when(coderHackServiceMock.fetchUserByIdService("userId1")).thenReturn(userDto);

      MvcResult result = mvc.perform(get(uri.toString()))
                            .andExpect(status().isOk())
                            .andReturn();

        verify(coderHackServiceMock, times(1)).fetchUserByIdService("userId1");

    }

    @Test
    void testFetchUsers() throws Exception {

        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI)
        .build()
        .toUri();
  
        assertEquals(USER_REGISTRATION_API_URI , uri.toString());

        UserDto userDto = new UserDto("userId1", "Raj1", 65, Set.of("Code Master"));

        GetCoderHackResponse getCoderHackResponse = new GetCoderHackResponse(List.of(userDto));

        when(coderHackServiceMock.fetchUsersService()).thenReturn(getCoderHackResponse);

        MvcResult result = mvc.perform(get(uri.toString()))
                            .andExpect(status().isOk())
                            .andReturn();

        verify(coderHackServiceMock, times(1)).fetchUsersService();

    }

    @Test
    void testRegisterUser() throws Exception {

        String requestBody = "{\r\n" + //
                        "    \"userName\": \"Raj1\",\r\n" + //
                        "    \"score\": 65,\r\n" + //
                        "    \"badges\": [\r\n" + //
                        "        \"b2\"\r\n" + //
                        "    ]\r\n" + //
                        "}";

        URI uri = UriComponentsBuilder
      .fromPath(USER_REGISTRATION_API_URI)
      .build().toUri();

      assertEquals(USER_REGISTRATION_API_URI , uri.toString());

      CoderHackResponse coderHackResponse = new CoderHackResponse("id1");
      User user = new User(null, "Raj1", 0, new HashSet<>());

      when(coderHackServiceMock.registerUserService(user)).thenReturn(coderHackResponse);

      when(coderHackServiceMock.registerUserService(user)).thenReturn(coderHackResponse);

      MvcResult result = mvc.perform(post(uri.toString())
                            .content(requestBody)
                            .contentType("application/json; charset=utf-8")
                            .accept(APPLICATION_JSON_VALUE))
                            .andExpect(status().isCreated())
                            .andReturn();


     verify(coderHackServiceMock, times(1)).registerUserService(user);

    }

    @Test
    void testRemoveUser() throws Exception {

        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI + ID)
        .buildAndExpand(USER_ID)
        .toUri();
  
        assertEquals(USER_REGISTRATION_API_URI +"/"+ USER_ID , uri.toString());
  
  
        doNothing().when(coderHackServiceMock).removeUserService("userId1");

        MvcResult result = mvc.perform(delete(uri.toString()))
                            .andExpect(status().isOk())
                            .andReturn();

        verify(coderHackServiceMock, times(1)).removeUserService("userId1");                    

    }

    @Test
    void testUpdateUserById() throws Exception {

        String requestBody = "{\r\n" + //
                        "    \"userId\": \"userId1\",\r\n" + //
                        "    \"userName\": \"Raj1\",\r\n" + //
                        "    \"score\": 65\r\n" + //
                        "}";


        URI uri = UriComponentsBuilder
        .fromPath(USER_REGISTRATION_API_URI + ID)
        .buildAndExpand(USER_ID)
        .toUri();
  
        assertEquals(USER_REGISTRATION_API_URI +"/"+ USER_ID, uri.toString());
  
        CoderHackResponse coderHackResponse = new CoderHackResponse("id1");
        User user = new User(null, "Raj1", 65, null);

        when(userRepositoryMock.findById("id1")).thenReturn(Optional.of(user));

        when(coderHackServiceMock.updateUserByIdService("id1", user)).thenReturn(coderHackResponse);
  

      MvcResult result = mvc.perform(put(uri.toString())
                            .content(requestBody)
                            .contentType("application/json; charset=utf-8")
                            .accept(APPLICATION_JSON_VALUE))
                            .andExpect(status().isCreated())
                            .andReturn();


       verify(coderHackServiceMock, times(1)).updateUserByIdService("userId1", user);

    }
}
