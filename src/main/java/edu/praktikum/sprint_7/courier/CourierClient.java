package edu.praktikum.sprint_7.courier;

import edu.praktikum.sprint_7.models.Courier;
import edu.praktikum.sprint_7.models.CourierCred;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String COURIER_URL = "/api/v1/courier";
    private static final String COURIER_LOGIN_URL = "/api/v1/courier/login";
    private static final String COURIER_ID = "/api/v1/courier/:id";
    @Step("Создание курьера {courier}")
    public Response create(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_URL);

    }
    @Step("Авторизация курьера с кредами {courierCred}")
    public Response login(CourierCred courierCred){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCred)
                .when()
                .post(COURIER_LOGIN_URL);
    }
    @Step("Удаление курьера")
    public Response deleteCourier(int id){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(String.format("{\"id\": \"%d\"}", id))
                .when()
                .delete(COURIER_ID);
    }
}
