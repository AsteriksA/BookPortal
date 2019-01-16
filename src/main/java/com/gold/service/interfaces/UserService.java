package com.gold.service.interfaces;

import com.gold.dto.UserDto;

public interface UserService extends BaseService<UserDto, Long> {

    UserDto findUserByEmail(String email);

    UserDto findByName(String name);

}
