import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.LoginInfo;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.yandex.practicum.scooter.api.model.CourierInfo.getRandomCourierInfo;

public class LoginCourierTest {

    public static final String NOT_FOUND_MESSAGE = "Учетная запись не найдена";
    public static final String BAD_REQUEST_MESSAGE = "Недостаточно данных для входа";

    int courierId;
    CourierInfo courierInfo;
    CourierInfo courierWithoutLogin;
    LoginInfo loginInfo;
    CourierClient courierClient = new CourierClient();


    @Before
    public void createData() {
        courierInfo = getRandomCourierInfo();
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());

    }

    @After
    public void clearData() {
        loginInfo = new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseLogin = courierClient.loginCourier(loginInfo);
        courierId = responseLogin.body().jsonPath().getInt("id");
        courierClient.deleteCourier(courierId);
    }

    @Test
    @Description("Позитивная проверка. Логиним курьера с логином и паролем")
    public void loginCourier() {
        loginInfo = new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseLogin = courierClient.loginCourier(loginInfo);
        assertEquals(SC_OK, responseLogin.statusCode());
        assertNotNull(responseLogin.body().jsonPath().getString("id"));

    }

    @Test
    @Description("Негативная проверка. Логиним курьера только с паролем, без логина")
    public void loginCourierWithoutLogin() {
        courierWithoutLogin = new CourierInfo(courierInfo.getPassword(), courierInfo.getFirstName());
        Response responseLoginWithoutLogin = courierClient.loginCourierWithoutLogin(courierWithoutLogin);
        assertEquals(SC_BAD_REQUEST, responseLoginWithoutLogin.statusCode());
        assertEquals(BAD_REQUEST_MESSAGE, responseLoginWithoutLogin.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Негативная проверка. Логиним курьера c неправильным логином")
    public void loginCourierWithIncorrectLogin() {
        loginInfo = new LoginInfo(courierInfo.getLogin() + "1", courierInfo.getPassword());
        Response responseWithIncorrectLogin = courierClient.loginCourier(loginInfo);
        assertEquals(SC_NOT_FOUND, responseWithIncorrectLogin.statusCode());
        assertEquals(NOT_FOUND_MESSAGE, responseWithIncorrectLogin.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Негативная проверка. Логиним курьера c неправильным паролем")
    public void loginCourierWithIncorrectPassword() {
        loginInfo = new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword() + "1");
        Response responseWithIncorrectPassword = courierClient.loginCourier(loginInfo);
        assertEquals(SC_NOT_FOUND, responseWithIncorrectPassword.statusCode());
        assertEquals(NOT_FOUND_MESSAGE, responseWithIncorrectPassword.body().jsonPath().getString("message"));

    }
}
