package ru.dmitry.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.dmitry.library.domain.User;
import ru.dmitry.library.mappers.UserMapper;

import java.security.MessageDigest;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByLoginAndPassword(String login, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return userMapper.findByLoginAndPassword(login, password);
    }
}
