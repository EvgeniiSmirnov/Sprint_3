package ru.yandex.practicum.scooter.api.model;

public class LoginInfo {
    public String login;
    public String password;

    public LoginInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
