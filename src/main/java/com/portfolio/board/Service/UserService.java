package com.portfolio.board.Service;


import com.portfolio.board.DTO.UserDTO;
import com.portfolio.board.Mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    public void save(UserDTO userDTO, String phonePrefix, String phoneMiddle, String phoneLast) {
        // 전화번호 조합
        String formattedPhone = String.format("%s-%s-%s", phonePrefix, phoneMiddle, phoneLast);
        userDTO.setUserPhone(formattedPhone);

        userMapper.save(userDTO);
    }

    public UserDTO login(UserDTO userDTO) {
        UserDTO userDTOFromDB = userMapper.findByUserEmail(userDTO.getUserEmail());
        if(userDTOFromDB != null){
            //해당 이메일을 가진 회원 정보가 있음.
            if(userDTOFromDB.getUserPW().equals(userDTO.getUserPW())) {
                //이메일 있고, 비밀번호 일치
                return userDTOFromDB;
            } else {
                //이메일 있지만, 비밀번호 불일치
                return null;
            }
        } else {
            //해당 이메일을 가진 회원 정보가 없음.
            return null;
        }
    }
    public boolean isEmailExists(String email) {
        return userMapper.existsByUserEmail(email);
    }

    public UserDTO autoLogin(String userEmail) {
        return userMapper.findByUserEmail(userEmail); // Mapper가 DTO를 바로 반환
    }
}
