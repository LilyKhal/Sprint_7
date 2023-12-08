package edu.praktikum.sprint_7.models;

public class CourierCred {
    private String login;
    private String password;

    public CourierCred(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCred fromCourier(Courier courier){
        return new CourierCred(courier.getLogin(), courier.getPassword());
    }
}
