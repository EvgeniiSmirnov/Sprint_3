package ru.yandex.practicum.scooter.api.model;

public class CourierWithoutLogin {
    private String password;
    private String firstName;

    public CourierWithoutLogin(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }
}
