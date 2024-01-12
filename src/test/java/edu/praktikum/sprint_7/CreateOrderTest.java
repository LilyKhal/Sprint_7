package edu.praktikum.sprint_7;
import edu.praktikum.sprint_7.models.Order;
import edu.praktikum.sprint_7.order.OrderClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private final  List<String> color;
    OrderClient client;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
        client = new OrderClient();
    }
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()},
        };
    }

    @Test
     public void createOrderTest(){
        Order order = new Order("Yuriy",
                                "Voron",
                                "Irkutsk",
                                "Slavanka",
                                "8907863001",
                                "1",
                                "2023-11-10",
                                "fgcudygc",
                                color
                                );
        Response createOrderResponse = client.create(order);
        assertEquals("Неверный статус код (create)",201, createOrderResponse.statusCode());
        assertNotNull("Тело ответа не верное (create)", createOrderResponse.path("track"));
    }



}
