
import POJO.request.*;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;


@RunWith(Parameterized.class)
public class TestCreateOrder {

    private OrderClient orderClient;
    private Order order;
   private static String [] color;

    public TestCreateOrder(String[] color) {
        this.color = color;
   }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }


    @Before
    public void setUp() {
        order = new Order("Павел", "Сумкин", "улица котиков", "молодежная", "89999996709", 5, "2020-08-08", "good lucky", color);
        orderClient = new OrderClient();
    }
    @DisplayName("Successful create order")
    @Test
    public void successCreateOrder() {
        ValidatableResponse response = orderClient.create(order);


        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(201, actualStatusCode);

        int actualMessage = response.extract().path("track");

        MatcherAssert.assertThat(actualMessage, notNullValue());



    }

}
