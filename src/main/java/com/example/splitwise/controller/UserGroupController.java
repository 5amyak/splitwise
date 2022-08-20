package com.example.splitwise.controller;

import com.example.splitwise.dto.UserGroupDto;
import com.example.splitwise.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user-groups")
@Slf4j
public class UserGroupController {

    private final UserGroupService userGroupService;

    @Autowired
    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @PostMapping
    public ResponseEntity<UserGroupDto> createUserGroup(@RequestBody @Valid UserGroupDto userGroupDto) {
        try {
            userGroupDto = userGroupService.createUserGroup(userGroupDto);

            log.info("UserGroup successfully created with id :: {}", userGroupDto.getUserGroupId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userGroupDto);
        } catch (Exception e) {
            log.error("Failed to create user group due to {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{userGroupId}/users")
    public ResponseEntity<UserGroupDto> addUsersToUserGroup(@PathVariable Long userGroupId,
                                                            @RequestBody List<String> emailList) {
        try {
            UserGroupDto userGroupDto = userGroupService.addUsersToUserGroup(userGroupId, emailList);

            log.info("Users added successfully to user group with id :: {}", userGroupDto.getUserGroupId());
            return ResponseEntity.ok()
                    .body(userGroupDto);
        } catch (Exception e) {
            log.error("Failed to add users to user group due to {}", e.getMessage());
            throw e;
        }
    }
}
