package edu.praktikum.sprint_7;

import edu.praktikum.sprint_7.courier.CourierClient;
import edu.praktikum.sprint_7.models.Courier;
import edu.praktikum.sprint_7.models.CourierCred;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint_7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint_7.models.CourierCred.fromCourier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {
    Courier courier;
    CourierClient courierClient;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
        courier = randomCourier();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }
    @Test
    public void loginCourierTest() {
        Response response = courierClient.login(fromCourier(courier));
        assertEquals("Неверный статус код (login)",
                200,
                response.statusCode());
        assertNotNull("Тело ответа не верное (login)", response.path("id"));
    }
    @Test
    public void loginWithWrongCredsTest(){
        Response wrongLoginResponse = courierClient.login(new CourierCred(courier.getLogin() + "q", courier.getPassword()));
        assertEquals("Неверный статус код (login)",
                404,
                wrongLoginResponse.statusCode());
        assertEquals("Тело ответа не верное (login)",
                    "Учетная запись не найдена",
                    wrongLoginResponse.path("message"));

        Response wrongPasswordResponse = courierClient.login(new CourierCred(courier.getLogin(), courier.getPassword() + "1"));
        assertEquals("Неверный статус код (login)",
                404,
                wrongPasswordResponse.statusCode());
        assertEquals("Тело ответа не верное (login)",
                "Учетная запись не найдена",
                wrongPasswordResponse.path("message"));

    }
    @Test
    public void loginWithoutSomeCredsTest(){
        Response withoutLoginResponse = courierClient.login(new CourierCred( "", courier.getPassword()));
        assertEquals("Неверный статус код (login)",
                400,
                withoutLoginResponse.statusCode());
        assertEquals("Тело ответа не верное (login)",
                "Недостаточно данных для входа",
                withoutLoginResponse.path("message"));

        Response withoutPasswordResponse = courierClient.login(new CourierCred(courier.getLogin(), ""));
        assertEquals("Неверный статус код (login)",
                400,
                withoutPasswordResponse.statusCode());
        assertEquals("Тело ответа не верное (login)",
                "Недостаточно данных для входа",
                withoutPasswordResponse.path("message"));
    }
    @After
    public void deleteCourier(){
        Response loginResponse = courierClient.login(fromCourier(courier));
        int id = loginResponse.path("id");
        courierClient.deleteCourier(id);
    }
}
