import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.CourierWithoutFirstname;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.CourierInfo.getRandomCourierInfo;

public class CreateCourierTest {
    int courierId;
    CourierInfo courierInfo;
    CourierWithoutFirstname courierWithoutFirstname;
    CourierClient courierClient = new CourierClient();


    @Before
    public void createData() {
        courierInfo = getRandomCourierInfo();

    }

    @After
    public void clearData() {
        courierWithoutFirstname = new CourierWithoutFirstname(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseLogin = courierClient.loginCourier(courierWithoutFirstname);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");
        courierClient.deleteCourier(courierId);
    }

    @Test
    @Description("Позитивная проверка. Создаём нового курьера. Все требуемые поля заполнены")
    public void createNewCourier() {
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertTrue(responseCreate.body().jsonPath().getBoolean("ok"));

    }

    @Test
    @Description("Негативная проверка. Нельзя создать двух одинаковых курьеров")
    public void createDoubleCourier() {
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        Response responseDoubleCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CONFLICT, responseDoubleCreate.statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", responseDoubleCreate.body().jsonPath().getString("message"));

    }

}
