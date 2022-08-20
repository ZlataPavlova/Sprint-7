import POJO.request.*;
import POJO.response.ResponseMessage;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;

public class TestLoginCourier {

    private Courier courier;
    private CourierClient courierClient;
    private ResponseMessage responseMessage;
    private int courierId;

    @Before
    public void setUp() {

        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
        responseMessage = new ResponseMessage();

    }

    @After
    public void tearDown(){

        courierClient.delete(courierId);

    }

    @Step("Send POST request to /api/v1/courier")
    public ValidatableResponse createCorrectCourier() {
        ValidatableResponse response = courierClient.create(courier);
        return response;
    }

    @Step("Send POST request to /api/v1/courier without login field")
    public Courier createCorrectCourierWithoutFieldLogin() {
        courier = CourierGenerator.withoutFieldLoginInLogInCourier();
        return courier;
    }

    @Step("Send POST request to /api/v1/courier without password field")
    public Courier createCorrectCourierWithoutFieldPassword() {
        courier = CourierGenerator.withoutFieldPasswordInLogInCourier();
        return courier;
    }

    @Step("Send POST request to api/v1/courier/login")
    public ValidatableResponse logInCourier() {
        ValidatableResponse loginResponse = courierClient.login( CourierCredentials.from(courier));
        return loginResponse;
    }
    @Step("Send POST request to api/v1/courier/login with incorrect login")
    public ValidatableResponse logInCourierWithIncorrectLogin() {
        ValidatableResponse loginResponse = courierClient.login( CourierCredentials.getWrongInFieldLogin(courier));
        return loginResponse;
    }

    @Step("Send POST request to api/v1/courier/login with incorrect password")
    public ValidatableResponse logInCourierWithIncorrectPassword() {
        ValidatableResponse loginResponse = courierClient.login( CourierCredentials.getWrongInFieldLogin(courier));
        return loginResponse;
    }

    @Step("Send POST request to api/v1/courier/login with non-existing password and login")
    public ValidatableResponse logInCourierWithNonExistPasswordAndLogin() {
        ValidatableResponse loginResponse = courierClient.login( CourierCredentials.getNotFoundLoginAndPassword(courier));
        return loginResponse;
    }

    @Step("Assert status code is 200")
    public void compareStatusCodeTo200 (ValidatableResponse loginResponse){
        int actualStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals(SC_OK, actualStatusCode);
    }

    @Step("Assert status code is 404")
    public void compareStatusCodeTo404 (ValidatableResponse loginResponse){
        int actualStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals(SC_NOT_FOUND, actualStatusCode);
    }

    @Step("Assert status code is 400")
    public void compareStatusCodeTo400 (ValidatableResponse loginResponse){
        int actualStatusCode = loginResponse.extract().statusCode();
        Assert.assertEquals(SC_BAD_REQUEST, actualStatusCode);
    }


    @Step("Assert 404 error message")
    public void compareMessageToError404(ValidatableResponse loginResponse, ResponseMessage responseMessage){
        String actualMessage = loginResponse.extract().path("message");
        Assert.assertEquals(responseMessage.getMessageError404(), actualMessage);
    }

    @Step("Assert 400 error message")
    public void compareMessageToError400(ValidatableResponse loginResponse, ResponseMessage responseMessage){
        String actualMessage = loginResponse.extract().path("message");
        Assert.assertEquals(responseMessage.getMessageError400LogInCourier(), actualMessage);
    }

    @Step("Get created courier id")
    public int getIdCreateCourier(ValidatableResponse loginResponse) {
        courierId = loginResponse.extract().path("id");
        return courierId;
    }
    @Step("Check response is not null")
    public void checkResponseNotNull (){
        MatcherAssert.assertThat(courierId, notNullValue());
    }



    @DisplayName("Courier log in")
    @Test
    public void successfullyLogIn() {
        createCorrectCourier();
        ValidatableResponse loginResponse = logInCourier();
        compareStatusCodeTo200 (loginResponse);
        getIdCreateCourier(loginResponse);
        checkResponseNotNull();
    }


    @DisplayName("Courier log in with incorrect login")
    @Test
    public void logInWithWrongLoginReturnWrongMessage() {
        createCorrectCourier();
        ValidatableResponse loginResponse = logInCourierWithIncorrectLogin();
        compareMessageToError404(loginResponse,  responseMessage);
        compareStatusCodeTo404 (loginResponse);

    }

    @DisplayName("Courier log in with incorrect password")
    @Test
    public void logInWithWrongPasswordReturnWrongMessage() {
        createCorrectCourier();
        ValidatableResponse loginResponse = logInCourierWithIncorrectPassword();
        compareMessageToError404(loginResponse,  responseMessage);
        compareStatusCodeTo404 (loginResponse);
    }

    @DisplayName("Courier log in without login field")
    @Test
    public void logInWithoutFieldLoginReturnWrongMessage() {

        courier = createCorrectCourierWithoutFieldLogin();
        System.out.println(courier.getPassword());
        ValidatableResponse loginResponse = logInCourier();
        compareStatusCodeTo400 (loginResponse);
       compareMessageToError400(loginResponse,responseMessage);

    }

    @DisplayName("Courier log in without password field")
    @Test
    public void logInWithoutFieldPasswordReturnWrongMessage() {

        courier = createCorrectCourierWithoutFieldPassword();
        ValidatableResponse loginResponse = logInCourier();
        compareStatusCodeTo400 (loginResponse);
        compareMessageToError400(loginResponse, responseMessage);

    }

    @DisplayName("courier is authorized with non-existing password and login")
    @Test
    public void logInWithNotFoundParamsReturnWrongMessage() {
        createCorrectCourier();
        ValidatableResponse loginResponse = logInCourierWithNonExistPasswordAndLogin();
        compareMessageToError404(loginResponse,  responseMessage);
        compareStatusCodeTo404 (loginResponse);

    }

}