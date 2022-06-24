package com.sirenanalytics.worldbank_common.service.impl;


import com.sirenanalytics.worldbank_common.exception.ErrorCode;
import com.sirenanalytics.worldbank_common.model.entity.Role;
import com.sirenanalytics.worldbank_common.model.entity.User;
import com.sirenanalytics.worldbank_common.model.simple.ChangePasswordRequest;
import com.sirenanalytics.worldbank_common.repository.CommonPermissionRepository;
import com.sirenanalytics.worldbank_common.repository.CommonRoleRepository;
import com.sirenanalytics.worldbank_common.repository.CommonUserRepository;
import com.sirenanalytics.worldbank_common.service.CommonUserService;
import com.sirenanalytics.worldbank_common.util.ContextUtil;
import com.sirenanalytics.worldbank_common.util.EncryptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommonUserServiceImpl implements CommonUserService
{
    /*------------------------------------------------------------------------------+
    |    A password is valid if...                                                  |
    |    It contains at least 8 characters                                          |
    |    It contains at least one digit.                                            |
    |    It contains at least one upper case alphabet.                              |
    |    It contains at least one lower case alphabet.                              |
    |    It contains at least one special character which includes !@#$%&*()-+=^.   |
    |    It doesn't contain any white space.                                        |
    +------------------------------------------------------------------------------*/
    private static final String validPasswordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[:punct:])(?=\\S+$).{8,}$";
    private static final String lowerAlpha = "abcdefghijklmnopqrstuvwxyz";
    private static final String upperAlpha = lowerAlpha.toUpperCase();
    private static final String digits = "0123456789";
    private static final String specialChars = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";

    @Resource
    CommonUserRepository commonUserRepository;
    @Resource
    CommonRoleRepository commonRoleRepository;
    @Resource
    CommonPermissionRepository commonPermissionRepository;

    public Page<User> find(Pageable pageable)
    {
        Page<User> userPage = commonUserRepository.findAll(pageable);
        userPage.iterator().forEachRemaining(user ->
        {
            List<Role> roleList = commonRoleRepository.findByUserId(user.getId());
            roleList.forEach(role -> role.setPermissions(commonPermissionRepository.findByRoleId(role.getId())));
            user.setRoles(roleList);
        });
        return userPage;
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        Optional<User> optionalUser = commonUserRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            return optionalUser;

        List<Role> roleList = commonRoleRepository.findByUserId(optionalUser.get().getId());
        roleList.forEach(role -> role.setPermissions(commonPermissionRepository.findByRoleId(role.getId())));
        optionalUser.get().setRoles(roleList);

        return optionalUser;
    }

    @Override
    public Optional<User> findById(Long id)
    {
        Optional<User> optionalUser = commonUserRepository.findById(id);
        if (optionalUser.isEmpty())
            return optionalUser;

        List<Role> roleList = commonRoleRepository.findByUserId(id);
        roleList.forEach(role -> role.setPermissions(commonPermissionRepository.findByRoleId(role.getId())));
        optionalUser.get().setRoles(roleList);

        return optionalUser;
    }

    public Page<User> getUsersWithRole(Pageable pageable, String roleName)
    {
        return commonUserRepository.getUsersWithRole(pageable, roleName);
    }

    @Override
    public User save(User user, boolean passwordIsPlainText)
    {
        /*------------------------------------------------------+
        |   Whenever we save a user, if a plain text password   |
        |   is being passed in, we need to generate a new salt  |
        |   and rehash the pw to a new digest.                  |
        +------------------------------------------------------*/
        if (passwordIsPlainText)
        {
            user.setPassword(new EncryptionUtil().encode(user.getPassword()));
            user.setChangePasswordRequired(false);
        }

        /*------------------------------------------------------+
        |   If this is a new user, always force changePassword  |
        +------------------------------------------------------*/
        if (user.getId() == null)
            user.setChangePasswordRequired(true);

        else if (user.getChangePasswordRequired() == null)
            user.setChangePasswordRequired(false);

        if (user.getEnabled() == null)
            user.setEnabled(false);

        return commonUserRepository.save(user);
    }

    @Override
    public Boolean doesUsernameExist(String username)
    {
        return commonUserRepository.doesUsernameExist(username);
    }

    @Override
    @Transactional
    public void setUserRoles(Long userId, List<Long> roleIdList)
    {
        Long modifyingUserId = ContextUtil.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        commonUserRepository.deleteUserRolesByUserId(userId);
        for (Long roleId : roleIdList)
            commonUserRepository.insertUserRoleRecord(userId, roleId, modifyingUserId, now);
    }

    @Override
    public ErrorCode updateUserPassword(String username, ChangePasswordRequest changePasswordRequest)
    {
        Optional<User> optionalUser = commonUserRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            return ErrorCode.INVALID_USER;

        User user = optionalUser.get();

        if (!new EncryptionUtil().matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            return ErrorCode.INVALID_PASSWORD;

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword()))
            return ErrorCode.PASSWORDS_DO_NOT_MATCH;

        if (!passwordIsValid(changePasswordRequest.getNewPassword()))
            return ErrorCode.PASSWORD_TOO_WEAK;

        user.setPassword(changePasswordRequest.getNewPassword());
        user.setChangePasswordRequired(false);
        save(user, true);
        return ErrorCode.NO_ERROR;
    }

    public boolean passwordIsValid(String password)
    {
        boolean hasLowerAlpha = StringUtils.containsAny(password, lowerAlpha);
        boolean hasUpperAlpha = StringUtils.containsAny(password, upperAlpha);
        boolean hasDigit = StringUtils.containsAny(password, digits);
        boolean hasSpecial = StringUtils.containsAny(password, specialChars);
        boolean isAtLeast8CharsLong = password.length() >= 8;

        return hasLowerAlpha && hasUpperAlpha && hasDigit && hasSpecial && isAtLeast8CharsLong;
    }
}
