package POJO.request;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierClient extends RestClient {

    private static final String COURIER_PATH  = "api/v1/courier";
    private static final String LOGIN_PATH  = "api/v1/courier/login";
    private static final String DELETE_PATH  = "api/v1/courier/";

    public ValidatableResponse create (Courier courier) {
            return given()
                    .spec(getBaseSpec())
                    .body(courier)
                    .when()
                    .post(COURIER_PATH)
                    .then();
    }

    public ValidatableResponse login (CourierCredentials courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    public ValidatableResponse delete (int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(DELETE_PATH+"{id}")
                .then();

    }

}
