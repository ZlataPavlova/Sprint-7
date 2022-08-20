package POJO.request;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String CREATE_ORDER_PATH  = "/api/v1/orders";


    public ValidatableResponse create (Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
    }

    public ValidatableResponse getListOrders () {
        return  given()
                .spec(getBaseSpec())
                .when()
                .get(CREATE_ORDER_PATH)
                .then();//.log().all();
    }

}
