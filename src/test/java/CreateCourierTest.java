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
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.CourierInfo.getRandomCourierInfo;

public class CreateCourierTest {

    public static final String BAD_REQUEST_MESSAGE = "Недостаточно данных для создания учетной записи";
    public static final String CONFLICT_MESSAGE = "Этот логин уже используется. Попробуйте другой.";

    int courierId;
    CourierInfo courierInfo;
    CourierInfo courierWithoutLogin;
    CourierInfo courierWithoutPassword;
    LoginInfo loginInfo;
    CourierClient courierClient = new CourierClient();

    @Before
    public void createData() {
        courierInfo = getRandomCourierInfo();

    }

    @After
    public void clearData() throws NullPointerException {
        try {
            loginInfo = new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword());
            Response responseLogin = courierClient.loginCourier(loginInfo);
            courierId = responseLogin.body().jsonPath().getInt("id");
            courierClient.deleteCourier(courierId);
        } catch (Exception exception) {
            System.out.println("Тест завершён без создания курьера.");
        }
    }

    @Test
    @Description("Позитивная проверка. Создаём нового курьера. Все требуемые поля заполнены")
    public void createNewCourier() {
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertTrue(responseCreate.body().jsonPath().getBoolean("ok"));

    }

    @Test
    @Description("Негативная проверка. Нельзя создать курьера без логина")
    public void createCourierWithoutLogin() {
        courierWithoutLogin = new CourierInfo(courierInfo.getPassword(), courierInfo.getFirstName());
        Response responseWithoutLogin = courierClient.createCourierWithoutLogin(courierWithoutLogin);
        assertEquals(SC_BAD_REQUEST, responseWithoutLogin.statusCode());
        assertEquals(BAD_REQUEST_MESSAGE, responseWithoutLogin.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Негативная проверка. Нельзя создать курьера без пароля")
    public void createCourierWithoutPassword() {
        courierWithoutPassword = new CourierInfo(courierInfo.getLogin());
        Response responseWithoutPassword = courierClient.createCourierWithoutPassword(courierWithoutPassword);
        assertEquals(SC_BAD_REQUEST, responseWithoutPassword.statusCode());
        assertEquals(BAD_REQUEST_MESSAGE, responseWithoutPassword.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Негативная проверка. Нельзя создать двух одинаковых курьеров")
    public void createDoubleCourier() {
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        Response responseDoubleCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CONFLICT, responseDoubleCreate.statusCode());
        assertEquals(CONFLICT_MESSAGE, responseDoubleCreate.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Позитивная проверка. Возможно создать курьера без поля Firstname")
    public void createCourierWithoutFirstname() {
        loginInfo = new LoginInfo(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseWithoutFirstname = courierClient.createCourierWithoutFirstname(loginInfo);
        assertEquals(SC_CREATED, responseWithoutFirstname.statusCode());
        assertTrue(responseWithoutFirstname.body().jsonPath().getBoolean("ok"));

    }

}
