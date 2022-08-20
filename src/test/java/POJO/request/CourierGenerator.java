package POJO.request;


public class CourierGenerator {

    public static Courier getDefault() {
        return new Courier("сема4", "0000", "сема");
    }
    public static Courier repeatLogin() {
        return new Courier("сема4", "0044", "сема4");
    }

    public static Courier withoutFieldLogin() {
        return new CourierBuilder().setPasswordAndFirstName("5656", "иван").build();
    }

    public static Courier withoutFieldPassword() {
        return new CourierBuilder().setLoginAndFirstName("zaichik200", "иван").build();
    }

    public static Courier withoutFieldLoginInLogInCourier() {
        return new CourierBuilder().setPassword("dfgrt").build();
    }

    public static Courier withoutFieldPasswordInLogInCourier() {
        return new CourierBuilder().setLogin("zaichik200").build();
    }
}
