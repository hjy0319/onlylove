package com.hujy.onlylove.service;

import com.hujy.onlylove.entity.User;

/**
 * Description
 *
 * @author hujy
 * @version 1.0
 * @date 2020-09-22 17:44
 */
public interface UserService {
    void save(User user);

    Integer getId(String nickName);

}
