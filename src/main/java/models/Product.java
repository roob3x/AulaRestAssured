package models;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class Product {
    private String nome;
    private int preco;
    private String descricao;
    private int quantidade;

    private String productId;
    private String token;

    public Product(String token,String nome, int preco, String descricao, int quantidade){
        this.token = token;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Response registerProductRequest() {
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", this.nome);
        userJsonRepresentation.put("preco", this.preco);
        userJsonRepresentation.put("descricao", this.descricao);
        userJsonRepresentation.put("quantidade", this.quantidade);

        Response registerUserResponse =
                given()

                        .header("Content-Type", "application/json")
                        .header("authorization",this.token)
                        .and()
                        .body(userJsonRepresentation.toJSONString())
                        .when()
                        .post("http://localhost:3000/usuarios");

        JsonPath jsonPathEvaluator = registerUserResponse.jsonPath();
        setProductId(jsonPathEvaluator.get("_id"));

        return registerUserResponse;

    }


    public Response deleteProductRequest(RequestSpecification spec){

        Response deleteUserResponse = given().spec(spec)
                .header("Content-Type","application/json")
                .header("authorization",getToken())
                .when()
                .delete("produtos/"+this.productId);
        return deleteUserResponse;
    }
}
