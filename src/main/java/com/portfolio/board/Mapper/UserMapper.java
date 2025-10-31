package com.portfolio.board.Mapper;

import com.portfolio.board.DTO.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void save(UserDTO userDTO);
    UserDTO findByUserEmail(String email);
    boolean existsByUserEmail(String userEmail);
}
