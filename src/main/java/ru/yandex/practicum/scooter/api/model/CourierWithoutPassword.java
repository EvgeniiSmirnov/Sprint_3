package ru.yandex.practicum.scooter.api.model;

public class CourierWithoutPassword {
    private String login;
    private String firstName;

    public CourierWithoutPassword(String login, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }
}