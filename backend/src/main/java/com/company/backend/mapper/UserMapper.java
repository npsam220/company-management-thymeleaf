package com.company.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.backend.dto.UserSearchDto;
import com.company.backend.entity.User;

@Mapper
public interface UserMapper {
    List<User> searchUsers(UserSearchDto dto);
}
