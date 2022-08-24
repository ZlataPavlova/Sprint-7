package POJO.response;

public class ResponseMessage {


    private  String messageError409  = "Этот логин уже используется. Попробуйте другой.";
    private  String messageError400CreateCourier  = "Недостаточно данных для создания учетной записи";
    private  String messageError400LogInCourier  ="Недостаточно данных для входа";
    private  String messageError404  = "Учетная запись не найдена";
    private  boolean messageCreate  = true;

    public String getMessageError409() {
        return messageError409;
    }

    public boolean isMessageCreate() {
        return messageCreate;
    }

    public String getMessageError400CreateCourier() {
        return messageError400CreateCourier ;
    }

    public String getMessageError404() {
        return messageError404;
    }

    public String getMessageError400LogInCourier() {
        return messageError400LogInCourier;
    }
}
