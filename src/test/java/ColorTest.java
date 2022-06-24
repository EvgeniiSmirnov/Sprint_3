import io.restassured.response.Response;
import jdk.jfr.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.model.Color;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.practicum.scooter.api.CourierSample.getRecSpec;

@RunWith(Parameterized.class)
public class ColorTest {
    private final String firstName = RandomStringUtils.randomAlphabetic(7);
    private final String lastName = RandomStringUtils.randomAlphabetic(7);
    private final String address = RandomStringUtils.randomAlphabetic(7) + " Street, " + RandomStringUtils.randomNumeric(2) + " apt.";
    private final int metroStation = 5;
    private final String phone = "+7 900 " + RandomStringUtils.randomNumeric(7);
    private final int rentTime = 5;
    private final String deliveryDate = "2022-01-01";
    private final String comment = RandomStringUtils.randomAlphabetic(7);
    private final String colorData;

    public ColorTest(String color) {
        this.colorData = color;

    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {"GREY"},
                {"BLACK"},
                {"BLACK, GREY"},
                {""},
        };
    }

    @Test
    @Description("Создание заказа с параметрами")
    public void createOrderWithColorParam() {
        Color color = new Color();
        String actual = "{\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"address\":\"" + address + "\","
                + "\"metroStation\":\"" + metroStation + "\","
                + "\"phone\":\"" + phone + "\","
                + "\"rentTime\":\"" + rentTime + "\","
                + "\"deliveryDate\":\"" + deliveryDate + "\","
                + "\"comment\":\"" + comment + "\","
                + "\"color\":[\"" + color.coloring(colorData) + "\"]}";

        Response response = given()
                .spec(getRecSpec())
                .body(actual)
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders");

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);

    }

}
