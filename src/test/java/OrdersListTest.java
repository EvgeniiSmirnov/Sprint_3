import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.CourierInfo;
import ru.yandex.practicum.scooter.api.model.CourierWithoutFirstname;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.yandex.practicum.scooter.api.model.CourierInfo.getRandomCourierInfo;

public class OrdersListTest {
    int courierId;
    CourierInfo courierInfo;
    CourierWithoutFirstname courierWithoutFirstname;
    CourierClient courierClient = new CourierClient();

    @Test
    @Description("Проверка списка заказов")
    public void createCourierWithoutLogin() {
        courierInfo = getRandomCourierInfo();
        Response responseCreate = courierClient.createCourier(courierInfo);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        courierWithoutFirstname = new CourierWithoutFirstname(courierInfo.getLogin(), courierInfo.getPassword());
        Response responseLogin = courierClient.loginCourier(courierWithoutFirstname);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");

        Response responseOrdersList = courierClient.getOrdersList(courierId);
        assertEquals(SC_OK, responseOrdersList.statusCode());
        assertNotNull(responseOrdersList.body().jsonPath().getString("orders"));

        courierClient.deleteCourier(courierId);

    }
}
