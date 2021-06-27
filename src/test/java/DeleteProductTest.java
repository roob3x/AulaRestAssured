import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class DeleteProductTest extends TestBase{
    private static User validUser;
    private static Product product;
    private String token;


    @BeforeClass
    public void LoginAndGetTokenAndcreateProduct(){
        validUser = new User("Roberto Filho","robertofilho@hotmail.com","7654321","true");
        validUser.registerUserRequest();
        Response loginResponse = validUser.authenticateUser();
        token = loginResponse.getBody().jsonPath().get("authorization").toString();
        product =  new Product(token,"Iphone 12 pro max",8200,"Apple iphone 12 pro max space gray 256gb",200);
        product.registerProductRequest();
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
