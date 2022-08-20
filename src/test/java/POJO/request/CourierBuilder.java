package POJO.request;

public class CourierBuilder {

    private String login=null;
    private String password=null;
    private String firstName;

    public CourierBuilder setPasswordAndFirstName(String password, String firstName){
        this.password=password;
        this.firstName=firstName;
        return this;
    }

    public CourierBuilder setLoginAndFirstName(String login, String firstName){
        this.login=login;
        this.firstName=firstName;
        return this;
    }

    public CourierBuilder setPassword(String password){
        this.password=password;

        return this;
    }

    public CourierBuilder setLogin(String login){
        this.login=login;

        return this;
    }

    public Courier build() {
        return new Courier(login, password, firstName);
    }

}
