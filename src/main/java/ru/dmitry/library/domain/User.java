package ru.dmitry.library.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class User {

    private String login;
    private String password;
    private Boolean isAdmin;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        return Objects.equal(login, user.login)
                && Objects.equal(password, user.password)
                && Objects.equal(isAdmin, user.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login, password, isAdmin);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("login", login)
                .add("password", password)
                .add("isAdmin", isAdmin)
                .toString();
    }
}
