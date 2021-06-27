import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostProductTest extends TestBase {
    private static User validUser;
    private String token;


    @BeforeClass
    public void dadosLogin(){
        validUser = new User("Roberto Filho","robertofilho@hotmail.com","7654321","true");
        validUser.registerUserRequest();


    }
    @BeforeClass
    public void loginAndGetToken(){
        Response loginResponse = validUser.authenticateUser();
        token = loginResponse.getBody().jsonPath().get("authorization").toString();
    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus201() {
        String json =
                "{\"nome\": \"Apple Mac mini\","+
                "  \"preco\": \"470\"," +
                "  \"descricao\": \"Mac mini 256gb m1\"," +
                "  \"quantidade\": \"200\"}";
        given()
                .spec(SPEC)
                .header("Content-Type","application/json")
                .header(            "Authorization", token)
                .and()
                .body(json)
                .when()
                .post("produtos")
                .then()
                .assertThat()
                .statusCode(201)
                .body("message",equalTo("Cadastro realizado com sucesso"));
    }

}
