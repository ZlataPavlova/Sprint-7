import POJO.request.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import POJO.response.ResponseMessage;

import static org.apache.http.HttpStatus.*;

public class TestCourier {

    private Courier courier;
    private Courier courierRepeatLogin;
    private CourierClient courierClient;
    private ValidatableResponse response;
    private ValidatableResponse loginResponse;
    private  ResponseMessage responseMessage;
    private int courierId;


   @Before
    public void setUp() {

       courier = CourierGenerator.getDefault();
       courierRepeatLogin = CourierGenerator.repeatLogin();
       courierClient = new CourierClient();
       responseMessage = new ResponseMessage();
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }


    @Step("Send POST request to /api/v1/courier")
    public ValidatableResponse checkCreateCorrectCourier() {
        ValidatableResponse response = courierClient.create(courier);
        return response;
    }

    @Step("Send POST request to /api/v1/courier with existing login courier")
    public ValidatableResponse createCorrectCourierWithRepeatLogin() {
        ValidatableResponse response = courierClient.create(courierRepeatLogin);
        return response;
    }

    @Step("Send POST request to /api/v1/courier without login field")
    public Courier createCorrectCourierWithoutFieldLogin() {
        courier = CourierGenerator.withoutFieldLogin();
        return courier;
    }

    @Step("Send POST request to /api/v1/courier without password field")
    public Courier createCorrectCourierWithoutFieldPassword() {
        courier = CourierGenerator.withoutFieldPassword();
        return courier;
    }

    @Step("Assert response status code is 201")
    public void compareStatusCodeTo201 (ValidatableResponse response){
        int actualStatusCode = response.extract().statusCode();
         Assert.assertEquals(SC_CREATED, actualStatusCode);
    }

    @Step("Assert successful message")
    public void compareMessageToSuccessfulMessage(ValidatableResponse response, ResponseMessage responseMessage){
        boolean actualMessage = response.extract().path("ok");
        Assert.assertEquals(responseMessage.isMessageCreate(), actualMessage);
    }

    @Step("Check successful LogIn with created courier")
    public ValidatableResponse checkLogInWithCreateCourier() {
        ValidatableResponse loginResponse = courierClient.login( CourierCredentials.from(courier));
        return loginResponse;
    }

    @Step("Get created courier id")
    public int getIdCreateCourier(ValidatableResponse loginResponse) {
        courierId = loginResponse.extract().path("id");
        return courierId;
    }

    @Step("Assert status code is 409")
    public void compareStatusCodeTo409(ValidatableResponse response){
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(SC_CONFLICT, actualStatusCode);
    }

    @Step("Assert status code is 400")
    public void compareStatusCodeTo400(ValidatableResponse response){
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(SC_BAD_REQUEST, actualStatusCode);
    }

    @Step("Assert 409 error message")
    public void compareMessageToError409(ValidatableResponse response, ResponseMessage responseMessage){
        String actualMessage = response.extract().path("message");
        Assert.assertEquals(responseMessage.getMessageError409(), actualMessage);
    }

    @Step("Assert 400 error message")
    public void compareMessageToError400(ValidatableResponse response, ResponseMessage responseMessage){
        String actualMessage = response.extract().path("message");
        Assert.assertEquals(responseMessage.getMessageError400CreateCourier(), actualMessage);
    }



    @Test
    @DisplayName("Successful create courier")
    public void createCorrectCourier() {
        ValidatableResponse response = checkCreateCorrectCourier();
        compareStatusCodeTo201(response);
        compareMessageToSuccessfulMessage(response, responseMessage);
        ValidatableResponse loginResponse = checkLogInWithCreateCourier();
        getIdCreateCourier(loginResponse);

    }


    @Test
    @DisplayName("Create a courier that already exists")

    public void createDoubleCourierReturnWrong() {
        checkCreateCorrectCourier();
        ValidatableResponse response = checkCreateCorrectCourier();
        compareStatusCodeTo409(response);
        compareMessageToError409( response, responseMessage);
        ValidatableResponse loginResponse = checkLogInWithCreateCourier();
        getIdCreateCourier(loginResponse);

   }

    @Test
    @DisplayName("Create a courier with a login that already exists")
    public void createDoubleLoginCourierReturnWrong() {

        checkCreateCorrectCourier();
        ValidatableResponse response = createCorrectCourierWithRepeatLogin();
        compareStatusCodeTo409(response);
        compareMessageToError409( response, responseMessage);

        ValidatableResponse loginResponse = checkLogInWithCreateCourier();
        getIdCreateCourier(loginResponse);
    }


    @Test
    @DisplayName("Create a courier without login field")
    public void createCourierWithoutFieldLogin() {

        courier = createCorrectCourierWithoutFieldLogin();
        ValidatableResponse response = checkCreateCorrectCourier();
        compareStatusCodeTo400(response);
        compareMessageToError400(response, responseMessage);

    }


   @Test
   @DisplayName("Create a courier without password field")
    public void createCourierWithoutFieldPassword() {

       courier = createCorrectCourierWithoutFieldPassword();
       ValidatableResponse response = checkCreateCorrectCourier();
       compareStatusCodeTo400(response);
       compareMessageToError400(response, responseMessage);
    }


}
