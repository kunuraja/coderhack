package com.crio.coderhack.exchange;

import java.util.List;

import com.crio.coderhack.dto.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetCoderHackResponse {

    List<UserDto> users;
}
