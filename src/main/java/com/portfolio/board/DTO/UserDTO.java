package com.portfolio.board.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private long id;
    private String userName;
    private String userPhone;
    private String userPW;
    private String userEmail;
    private String userBirth;
}
