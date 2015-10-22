package ru.dmitry.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.dmitry.library.domain.User;
import ru.dmitry.library.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider{

    @Autowired
    private UserService userService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password  = (String) authentication.getCredentials();
        User user = userService.findByLoginAndPassword(login, password);
        if (user == null) {
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.getIsAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    public boolean supports(Class<?> aClass) {
        return true;
    }
}
