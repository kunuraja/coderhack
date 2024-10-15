package com.crio.coderhack.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String userName;
    private Integer score;
    private Set<String> badges;
    
}
