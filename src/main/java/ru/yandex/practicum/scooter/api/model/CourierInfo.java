package ru.yandex.practicum.scooter.api.model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierInfo {
    private String login;
    private String password;
    private String firstName;

    public CourierInfo(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CourierInfo(String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }

    public CourierInfo(String login) {
        this.login = login;
    }

    public static CourierInfo getRandomCourierInfo() {
        String login = RandomStringUtils.randomAlphabetic(7);
        String password = RandomStringUtils.randomAlphabetic(7);
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return new CourierInfo(login, password, firstName);
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
