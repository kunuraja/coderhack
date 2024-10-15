package com.crio.coderhack.exchange;

import java.util.List;

import com.crio.coderhack.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCoderHackResponse {

    List<UserDto> users;
}
