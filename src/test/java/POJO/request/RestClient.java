package POJO.request;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;


public class RestClient {
    private static final String BASE_URL  = "http://qa-scooter.praktikum-services.ru/";

    public RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    }



