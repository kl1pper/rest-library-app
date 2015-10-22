package ru.dmitry.library.services;

import ru.dmitry.library.domain.User;

public interface UserService {
    User findByLoginAndPassword(String login, String password);
}
