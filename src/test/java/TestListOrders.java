import POJO.request.OrderClient;
import POJO.response.ListOrders;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.junit.Assert;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Before;
import org.junit.Test;




public class TestListOrders {
    private OrderClient orderClient;
    private  ListOrders listOrders;
    private ValidatableResponse response;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Step("Send GET request to '/api/v1/orders'")
   public ValidatableResponse sendResponseInJson(){
        ValidatableResponse response = orderClient.getListOrders();

        return response;
   }
   @Step("Send GET request to '/api/v1/orders' and deserialize into ListOrders class")
   public ListOrders sendResponseInListOrdersClass(ValidatableResponse response){
       ListOrders listOrders = response.extract().body().as(ListOrders.class);
        return listOrders;
   }
  @Step("Assert response status code is 200")
    public void compareStatusCodeToSomething(ValidatableResponse response){
       int orderStatusCode = response.extract().statusCode();
       Assert.assertEquals(200, orderStatusCode);
    }
   @Step("Check ListOrders object is not null")
   public void checkListOrdersIsNotNull(ListOrders listOrders){
       MatcherAssert.assertThat(listOrders.toString(), notNullValue());
   }
   @Step("Check ListOrders object is not empty")
    public void checkListOrdersIsNotEmpty(ListOrders listOrders){
      Assert.assertEquals(listOrders.toString().isEmpty(), false);
  }



    @Test
    @DisplayName("Test Get List Orders")
    public void successfullyGetListOrders() {
       ValidatableResponse response = sendResponseInJson();
        ListOrders listOrders = sendResponseInListOrdersClass(response);
        compareStatusCodeToSomething(response);
       checkListOrdersIsNotNull(listOrders);
       checkListOrdersIsNotEmpty(listOrders);

    }
}
