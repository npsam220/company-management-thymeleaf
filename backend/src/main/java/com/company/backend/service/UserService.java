package com.company.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.backend.dto.UserSearchDto;
import com.company.backend.entity.User;
import com.company.backend.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> searchUsers(UserSearchDto dto) {
        // ユーザー検索のロジックを実装
        return userMapper.searchUsers(dto);
    }
}
