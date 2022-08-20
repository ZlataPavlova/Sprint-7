package POJO.request;

public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials getWrongInFieldLogin (Courier courier){
    return new CourierCredentials(courier.wrongLogin(), courier.getPassword());
}
    public static CourierCredentials getWrongInFieldPassword (Courier courier){
        return new CourierCredentials(courier.wrongLogin(), courier.wrongPassword());
    }
    public static CourierCredentials getNotFoundLoginAndPassword (Courier courier){
        return new CourierCredentials(courier.wrongLogin(), courier.wrongPassword());
    }

}
