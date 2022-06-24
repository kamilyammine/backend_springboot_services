package com.sirenanalytics.worldbank_common.service;

import com.sirenanalytics.worldbank_common.exception.ErrorCode;
import com.sirenanalytics.worldbank_common.model.entity.User;
import com.sirenanalytics.worldbank_common.model.simple.ChangePasswordRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommonUserService
{
    Page<User> find(Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Page<User> getUsersWithRole(Pageable pageable, String roleName);

    User save(User user, boolean passwordIsPlainText);

    Boolean doesUsernameExist(String username);

    void setUserRoles(Long userId, List<Long> roleIdList);

    ErrorCode updateUserPassword(String username, ChangePasswordRequest changePasswordRequest);

    boolean passwordIsValid(String password);
}
