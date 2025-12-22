package com.ssafy.enjoytrip.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	// Mapper 객체 주입(DB 담당) 
	private final UserMapper userMapper;

	@Override
    @Transactional // 회원 가입 도중 에러나면 자동 롤백 처리
    public int joinUser(UserDto userDto) throws Exception {
        
        // 1. 아이디 중복 체크 (안전장치)
        if (userMapper.idCheck(userDto.getUserId()) > 0) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }

        // 2. 닉네임 중복 체크
        if (userMapper.countByNickname(userDto.getNickName()) > 0) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
        }

        // 3. 전화번호 중복 체크
        if (userMapper.countByPhoneNumber(userDto.getPhoneNumber()) > 0) {
            throw new IllegalStateException("이미 등록된 전화번호입니다.");
        }

        // 4. 이메일 중복 체크
        if (userMapper.emailCheck(userDto.getEmail()) > 0) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        // TODO : 비밀번호 암호화 로직 등 추가 가능
        
        // 모든 검증 통과 시 DB 저장
        return userMapper.joinUser(userDto);
    }
	@Override
	public UserDto loginUser(String userId, String password) throws Exception {
		// 로그인 로직: Mapper에게 아이디/ 비번 주고 일치하는 사람 있는지 물어봄
		return userMapper.loginUser(userId, password);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		// 0이면 사용 가능, 1이면 중복
		return userMapper.idCheck(userId);
	}
	
	@Override
    public UserDto getUserInfo(String userId) throws Exception {
        return userMapper.selectUserByUserId(userId);
    }

	@Override
	@Transactional
	public int modifyUser(UserDto userDto) throws Exception {
		// TODO: 비밀번호 변경 등 민감한 정보 수정 시 현재 비밀번호 확인 로직 등을 추가해야 합니다.
        // 현재는 닉네임, 이메일, 생년월일 등의 정보만 UserDto에 담겨 넘어온다고 가정합니다.
		return userMapper.updateUser(userDto);
	}

	@Override
	@Transactional
	public int withdrawUser(String userId) throws Exception {
		return userMapper.deleteUser(userId);
	}
	
	@Override
	public int emailCheck(String email) throws Exception {
	    return userMapper.emailCheck(email);
	}
	
	
    @Override
    public int nicknameCheck(String nickname) throws Exception {
        return userMapper.countByNickname(nickname);
    }

    
    @Override
    public int phoneNumberCheck(String phoneNumber) throws Exception {
        return userMapper.countByPhoneNumber(phoneNumber);
    }
    @Override
    public List<UserDto> listUser() throws Exception {
        return userMapper.selectAllUsers();
    }
}
