import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostLoginTests extends TestBase{
    private static User validUser;
    private static User invalidUser;

    @BeforeClass
    public static void generateTestData(){
        validUser = new User("Rob","rob@hotmail.com","123","true");
        validUser.registerUserRequest();
        invalidUser = new User("Rob","rob@hotmail.com","123456","true");

    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus200() {
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("email",validUser.email);
        userJsonRepresentation.put("password",validUser.password);
        given()
                .spec(SPEC)
                .header("Content-Type","application/json")
                .and()
                .body(validUser.getUserCredential())
                .when()
                .post("login")
                .then()
                .assertThat()
                .statusCode(200)
                .body("message",equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue());
    }

    @Test
    public void shouldReturnSucessMessageAuthTokenAndStatus200AndSaveAuthorization() {
        /**
         * //uma forma de fazer o request
       String credential = validUser.getUserCredential();
       Response loginResponse = given()
               .spec(SPEC)
               .header("Content-Type","application/json")
               .and()
               .body(credential)
               .when()
               .post("login");
       loginResponse.then()
                .assertThat()
                .statusCode(200)
                .body("message",equalTo("Login realizado com sucesso"));

//salvar a autorizacao para acessar o login
        JsonPath jsonPathEvaluator = loginResponse.jsonPath();
        String authToken = jsonPathEvaluator.get("authorization");
        validUser.setUserAuthToken(authToken);
         */

        Response loginResponse = validUser.authenticateUser();
        loginResponse.then().assertThat()
                .statusCode(200)
                .body("message",equalTo("Login realizado com sucesso"));
    }


@Test
    public void shouldReturnFailedMessageAuthTokenAndStatus400() {
        given()
                .spec(SPEC)
                .header("Content-Type","application/json")
                .and()
                .body(invalidUser.getUserCredential())
                .when()
                .post("login")
                .then()
                .assertThat()
                .statusCode(401)
                .body("message",equalTo("Email e/ou senha inválidos"))
                .body("authorization", nullValue());
    }
}
