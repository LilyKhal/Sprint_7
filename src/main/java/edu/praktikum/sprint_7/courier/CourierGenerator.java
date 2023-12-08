package edu.praktikum.sprint_7.courier;

import edu.praktikum.sprint_7.models.Courier;

import static edu.praktikum.sprint_7.utils.Utils.randomString;

public class CourierGenerator {


    public static Courier randomCourier() {

        return new Courier(randomString(6), randomString(8), randomString(11));
//                .withLogin(randomString())
//                .withPassword(randomString())
//                .withFirstName(randomString());
    }
}
