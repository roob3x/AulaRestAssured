import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.DataProvider;

public class GetUserTests extends TestBase{
    private static User validUser1;
    private static User validUser2;
    private static User invalidUser1;



    @BeforeClass
    public static void generateTestData(){
        validUser1 = new User("Marinha","maria@hotmail.com","123456","true");
        validUser1.registerUserRequest();
       // validUser2 = new User("Chico","chico@hotmail.com","123456","true");
        //validUser2.registerUserRequest();
        //invalidUser1 = new User("Manuel","manue@hotmail.com","kfsdkflsdk","0");
        validUser2.registerUserRequest();
    }

    @DataProvider(name = "usersData")
    public Object[][] createTestdata(){
        return new Object[][]{
                {"?nome="+validUser1.name,2},
                {"?password="+validUser1.password, 2},
                {"?nome="+invalidUser1.name+"&mail="+invalidUser1.email,0}
        };
    }
    @Test(dataProvider = "usersData")
    public void shouldReturnUsersAndStatus200(String query, int totalUser){
        given()
                .spec(SPEC)
                .when()
                .get("usuarios"+query)
                .then()
                .assertThat()
                .statusCode(200)
                .body("quantidade", equalTo(totalUser));
    }

    @AfterClass
    public void removeTestData(){
        validUser1.deleteUserRequest(SPEC);
    }
}
