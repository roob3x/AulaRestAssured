import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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

public class DeleteUserTest extends TestBase {
    private static User validUser;

    @BeforeClass
    public static void generateTestData() {
        validUser = new User("Tatu", "tatu@hotmail.com", "123", "true");
        validUser.registerUserRequest();
    }

    @Test
    public void shouldRemoverAndReturnSucessMessageAndStatus200(){
       Response deleteUserResponse = validUser.deleteUserRequest(SPEC);
               deleteUserResponse.then()
                .assertThat()
                .statusCode(200)
                .body("message",equalTo("Registro excluído com sucesso"));
    }
}
