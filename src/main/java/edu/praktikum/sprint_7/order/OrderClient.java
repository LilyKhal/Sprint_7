package edu.praktikum.sprint_7.order;

import edu.praktikum.sprint_7.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String ORDER_URL = "/api/v1/orders";
    @Step("Создание заказа")
    public Response create(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_URL);
    }
    @Step("Получение списка заказов")
    public Response getOrderList(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_URL);

    }
}
