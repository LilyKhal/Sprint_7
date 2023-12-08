package edu.praktikum.sprint_7;

import edu.praktikum.sprint_7.order.OrderClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void getOrderListTest(){
        OrderClient orderClient = new OrderClient();
        Response orderListResponse = orderClient.getOrderList();
        assertEquals("Неверный статус код (get order list)",200, orderListResponse.statusCode());
        assertNotNull("Тело ответа не верное (get order list)", orderListResponse.path("orders"));
    }
}
