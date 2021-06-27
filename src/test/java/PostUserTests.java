import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import models.User;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PostUserTests extends TestBase {

    private static User validUser;
    private static User invalidUser;

    //@BeforeClass
    public static void generateTestData(){
        validUser = new User("Roberto Filho","rob@hotmail.com","123","true");
        validUser.registerUserRequest();
        invalidUser = new User("Rob","rob@hotmail.com","123456","true");
    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus200() {
        String json = "{\"nome\": \"Roberto Filho\","+
                "  \"email\": \"robertopai@hotmail.com\"," +
                "  \"password\": \"7654321\"," +
                "  \"administrador\": \"true\"}";
        given()
                .spec(SPEC)
                .header("Content-Type","application/json")
                .and()
                .body(json)
                .when()
                .post("usuarios")
                .then()
                .assertThat()
                .statusCode(201)
                .body("message",equalTo("Cadastro realizado com sucesso"));

    }

}