import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.CourierWithoutFirstname;
import ru.yandex.practicum.scooter.api.model.CourierWithoutLogin;
import ru.yandex.practicum.scooter.api.model.CourierWithoutPassword;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.CourierInfo.getRandomCourierInfo;

public class CreateCourierWithoutParamTest {

    public static final String BAD_REQUEST_MESSAGE = "Недостаточно данных для создания учетной записи";

    CourierInfo courierInfo;
    CourierWithoutLogin courierWithoutLogin;
    CourierWithoutFirstname courierWithoutFirstname;
    CourierWithoutPassword courierWithoutPassword;
    CourierClient courierClient = new CourierClient();

    @Before
    public void createData() {
        courierInfo = getRandomCourierInfo();

    }

    @Test
    @Description("Негативная проверка. Нельзя создать курьера без логина")
    public void createCourierWithoutLogin() {
        courierWithoutLogin = new CourierWithoutLogin(courierInfo.getPassword(), courierInfo.getFirstName());
        Response responseWithoutLogin = courierClient.createCourierWithoutLogin(courierWithoutLogin);
        assertEquals(SC_BAD_REQUEST, responseWithoutLogin.statusCode());
        assertEquals(BAD_REQUEST_MESSAGE, responseWithoutLogin.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Негативная проверка. Нельзя создать курьера без пароля")
    public void createCourierWithoutPassword() {
        courierWithoutPassword = new CourierWithoutPassword(courierInfo.getLogin(), courierInfo.getFirstName());
        Response responseWithoutPassword = courierClient.createCourierWithoutPassword(courierWithoutPassword);
        assertEquals(SC_BAD_REQUEST, responseWithoutPassword.statusCode());
        assertEquals(BAD_REQUEST_MESSAGE, responseWithoutPassword.body().jsonPath().getString("message"));

    }

    @Test
    @Description("Позитивная проверка. Создаём нового курьера без поля Firstname")
    public void createCourierWithoutFirstname() {
        courierWithoutFirstname = new CourierWithoutFirstname(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseWithoutFirstname = courierClient.createCourierWithoutFirstname(courierWithoutFirstname);
        assertEquals(SC_CREATED, responseWithoutFirstname.statusCode());
        assertTrue(responseWithoutFirstname.body().jsonPath().getBoolean("ok"));

        Response responseLogin = courierClient.loginCourier(courierWithoutFirstname);
        assertEquals(SC_OK, responseLogin.statusCode());
        int courierId = responseLogin.body().jsonPath().getInt("id");
        courierClient.deleteCourier(courierId);

    }

}