package com.example.splitwise.mapper;

import com.example.splitwise.dto.UserDto;
import com.example.splitwise.dto.UserGroupDto;
import com.example.splitwise.model.UserGroup;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

public class UserGroupMapper {

    public static UserGroupDto toUserGroupDto(UserGroup userGroup, ModelMapper modelMapper) {
        return new UserGroupDto()
                .setUserGroupId(userGroup.getId())
                .setTitle(userGroup.getTitle())
                .setDescription(userGroup.getDescription())
                .setUserDtoList(userGroup.getUsers().stream()
                        .map(user -> modelMapper.map(user, UserDto.class))
                        .collect(Collectors.toList()));
    }
}
