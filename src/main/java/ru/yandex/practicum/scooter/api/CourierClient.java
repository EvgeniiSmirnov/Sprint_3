package ru.yandex.practicum.scooter.api;

import io.restassured.response.Response;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.CourierWithoutFirstname;
import ru.yandex.practicum.scooter.api.model.CourierWithoutLogin;
import ru.yandex.practicum.scooter.api.model.CourierWithoutPassword;

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


    public Response createCourierWithoutLogin(CourierWithoutLogin courierWithoutLogin) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutLogin)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response createCourierWithoutPassword(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutPassword)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response createCourierWithoutFirstname(CourierWithoutFirstname courierWithoutFirstname) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutFirstname)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }

    public Response loginCourier(CourierWithoutFirstname courierWithoutFirstname) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutFirstname)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response loginCourierWithoutLogin(CourierWithoutLogin courierWithoutLogin) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutLogin)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }

    public Response loginCourierWithoutPassword(CourierWithoutPassword courierWithoutPassword) {
        return given()
                .spec(getRecSpec())
                .body(courierWithoutPassword)
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
