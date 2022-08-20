package com.example.splitwise.service;

import com.example.splitwise.dto.UserGroupDto;

import java.util.List;

public interface UserGroupService {
    UserGroupDto createUserGroup(UserGroupDto userGroupDto);

    UserGroupDto addUsersToUserGroup(Long userGroupId, List<String> emailList);
}
