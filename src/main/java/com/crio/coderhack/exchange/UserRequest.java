package com.crio.coderhack.exchange;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private Integer score;
    private Set<String> badges;
    
}
