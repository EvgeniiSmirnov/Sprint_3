package ru.yandex.practicum.scooter.api.model;

public class CourierWithoutFirstname {
    public String login;
    public String password;

    public CourierWithoutFirstname(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
