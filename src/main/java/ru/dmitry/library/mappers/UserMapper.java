package ru.dmitry.library.mappers;

import org.apache.ibatis.annotations.Param;
import ru.dmitry.library.domain.User;

public interface UserMapper {
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
