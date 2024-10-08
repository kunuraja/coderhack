package com.crio.coderhack.service;

import com.crio.coderhack.dto.UserDto;
import com.crio.coderhack.exception.UserDontExistException;
import com.crio.coderhack.exchange.CoderHackResponse;
import com.crio.coderhack.exchange.GetCoderHackResponse;
import com.crio.coderhack.models.User;


public interface CoderHackService {
    public CoderHackResponse registerUserService(User user);
    public GetCoderHackResponse fetchUsersService() throws UserDontExistException;
    public UserDto fetchUserByIdService(String userId);
    public CoderHackResponse updateUserByIdService(String userId, User user);
    public void removeUserService(String userId);
}
