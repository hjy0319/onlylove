package com.hujy.onlylove.service.impl;

import com.hujy.onlylove.entity.User;
import com.hujy.onlylove.mapper.UserMapper;
import com.hujy.onlylove.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 17:46
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public Integer getId(String nickName) {
        return userMapper.getId(nickName);
    }
}
