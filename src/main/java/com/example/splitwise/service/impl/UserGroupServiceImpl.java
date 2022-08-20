package com.example.splitwise.service.impl;

import com.example.splitwise.dto.UserGroupDto;
import com.example.splitwise.mapper.UserGroupMapper;
import com.example.splitwise.model.User;
import com.example.splitwise.model.UserGroup;
import com.example.splitwise.repository.UserGroupRepository;
import com.example.splitwise.repository.UserRepository;
import com.example.splitwise.service.UserGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserGroupDto createUserGroup(UserGroupDto userGroupDto) {
        UserGroup userGroup = modelMapper.map(userGroupDto, UserGroup.class);
        userGroup = userGroupRepository.save(userGroup);

        return modelMapper.map(userGroup, UserGroupDto.class);
    }

    @Override
    public UserGroupDto addUsersToUserGroup(Long userGroupId, List<String> emailList) {
        UserGroup userGroup = userGroupRepository.findById(userGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Group Id"));
        List<User> userList = userRepository.findAllByEmailIn(emailList);

        userGroup.getUsers().addAll(userList);
        userGroup = userGroupRepository.save(userGroup);

        return UserGroupMapper.toUserGroupDto(userGroup, modelMapper);
    }
}
