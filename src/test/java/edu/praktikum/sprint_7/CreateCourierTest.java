package edu.praktikum.sprint_7;

import edu.praktikum.sprint_7.models.Courier;
import edu.praktikum.sprint_7.courier.CourierClient;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import io.restassured.response.Response;

import static edu.praktikum.sprint_7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint_7.models.CourierCred.fromCourier;
import static edu.praktikum.sprint_7.utils.Utils.randomString;
import static org.junit.Assert.assertEquals;

public class CreateCourierTest {
    CourierClient courierClient;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public void loginAndDelete(Courier courier) {
        Response loginResponse = courierClient.login(fromCourier(courier));
        int id = loginResponse.path("id");
        courierClient.deleteCourier(id);
//        Response deleteResponse = client.deleteCourier(id);
//        assertEquals("delete failed", 200, deleteResponse.statusCode());
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
        courierClient = new CourierClient();
    }

    @Test
    public void createCourierTest () {
        Courier courier = randomCourier();

        Response createResponse = courierClient.create(courier);
        assertEquals("Неверный статус код (create)",201, createResponse.statusCode());
        assertEquals("Тело ответа не верное (create)",true, createResponse.path("ok"));

        loginAndDelete(courier);
    }

    @Test
    public void createSameCourierTest() {
        Courier courier = randomCourier();

        courierClient.create(courier);

        Response duplicateCreateResponse = courierClient.create(courier);
        assertEquals("Неверный статус код (create)",409, duplicateCreateResponse.statusCode());
//        assertEquals("Тело ответа не совпадает", "Этот логин уже используется",duplicateCreateResponse.path("message"));
        loginAndDelete(courier);
    }
    @Test
    public void createCourierWithEmptyParameterTest(){
        Courier courierWithoutLogin = new Courier("","98765","Alex");

        Response courierWithoutLoginResponse  = courierClient.create(courierWithoutLogin);
        assertEquals("Неверный статус код", 400,courierWithoutLoginResponse.statusCode());
        assertEquals("Тело ответа не совпадает", "Недостаточно данных для создания учетной записи",courierWithoutLoginResponse.path("message"));

        Courier courierWithoutPassword = new Courier("GENRY12","","David");

        Response courierWithoutPasswordResponse  = courierClient.create(courierWithoutPassword);
        assertEquals("Неверный статус код", 400,courierWithoutPasswordResponse.statusCode());
        assertEquals("Тело ответа не совпадает", "Недостаточно данных для создания учетной записи",courierWithoutPasswordResponse.path("message"));
    }
    @Test
    public void createCourierWithSameLoginTest() {
        String login = randomString(8);
        Courier courier = new Courier(login, randomString(8), randomString(11));

        courierClient.create(courier);
        Courier courierWithSameLogin = new Courier(login, randomString(8), randomString(11));
        Response createCourierWithSameLoginResponse = courierClient.create(courierWithSameLogin);
        assertEquals("Неверный статус код (create)",409, createCourierWithSameLoginResponse.statusCode());
      //  assertEquals("Тело ответа не совпадает", "Этот логин уже используется",createCourierWithSameLoginResponse.path("message"));
        loginAndDelete(courier);
    }
}