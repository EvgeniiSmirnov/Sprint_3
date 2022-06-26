package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.LoginInfo;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient extends CourierSample {

    public Response createCourier(CourierInfo courierInfo) {
        return given()
                .spec(getRecSpec())
                .body(courierInfo)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }


    public Response createCourierWithoutLogin(CourierInfo courierWithoutLogin) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutLogin)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response createCourierWithoutPassword(CourierInfo courierWithoutPassword) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutPassword)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response createCourierWithoutFirstname(LoginInfo loginInfo) {
        return given()
                .spec(getRecSpec())
                .body(loginInfo)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response loginCourier(LoginInfo loginInfo) {
        return given()
                .spec(getRecSpec())
                .body(loginInfo)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response loginCourierWithoutLogin(CourierInfo courierWithoutLogin) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutLogin)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response deleteCourier(int courierId) {
        return given()
                .spec(getRecSpec())
                .when()
                .delete(BASE_URL + "/api/v1/courier/" + courierId)
                .then()
                .assertThat().statusCode(SC_OK)
                .extract().path("id");
    }

    public Response getOrdersList(int courierId) {
        return given()
                .spec(getRecSpec())
                .when()
                .get(BASE_URL + "/api/v1/orders?courierId=" + courierId);
    }

}
