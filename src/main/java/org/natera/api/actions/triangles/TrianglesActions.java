package org.natera.api.actions.triangles;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.natera.api.Constants;
import org.natera.api.classes.Triangles;
import org.natera.core.settings.Config;
import org.natera.core.testData.TestVariables;
import org.testng.Assert;
import java.io.IOException;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.natera.api.Constants.BASE_URL_TRIANGLE;
import static org.natera.core.helpers.ApiHelper.message;

public class TrianglesActions {
    static TestVariables vars = new TestVariables();
    private static String TRIANGLE_ENDPOINT = Config.CoreSettings.defaultEnvironment + "/triangle";
    private static String USER_TOKEN = Constants.USER_TOKEN;

    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addHeader("X-User", USER_TOKEN)
            .setBaseUri(BASE_URL_TRIANGLE)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL).build();

    @Step("Get all existing triangles and put them into a Set")
    public static Set<Triangles> getTriangles(boolean getId) throws IOException {
        message("> will get all existing triangles on the server");
        String json =  given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .get(TRIANGLE_ENDPOINT+"/all")
                .getBody()
                .prettyPrint();
        JsonElement parsed = new JsonParser().parse(json);
        JsonArray triangles = parsed.getAsJsonArray();
        if(triangles.size()>0&& getId)vars.setTriangleId(triangles.get(triangles.size()-1).
                getAsJsonObject().get("id").toString());
        return new Gson().fromJson(triangles,new TypeToken<Set<Triangles>>(){}.getType());
    }

    @Step("Get all existing triangles and check status code")
    public static Response getTrianglesAll(){
        message("> will get all existing Triangles on the server");
        Response resp =given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .get(TRIANGLE_ENDPOINT+"/all");
        return resp;
    }

    @Step("Try to get all triangles by not Unauthorized user")
    public static Response getTrianglesUnauthorized(){
        message("> will try get all triangles by not Unauthorized user");
        Response resp =given()
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .get(TRIANGLE_ENDPOINT+"/all");
        return resp;
    }

    @Step("Create a new triangle")
    public static String createTriangle() {
        message("> will create a new Triangle");
        String created = given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"separator\": \";\",\n" +
                        "  \"input\": \"3;4;7\""+
                        "}")
                .post(TRIANGLE_ENDPOINT)
                .prettyPrint();
        JsonElement parsed = new JsonParser().parse(created);
        JsonElement id = parsed.getAsJsonObject().get("id");
        return id.toString();
    }

    @Step("Create a custom triangle")
    public static String createTriangle(int firstSide, int secondSide, int thirdSide) {
        message("> will create a new custom Triangle");
        String created = given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"separator\": \";\",\n" +
                        "  \"input\": \""+firstSide+";"+secondSide+";"+thirdSide+";\""+
                        "}")
                .post(TRIANGLE_ENDPOINT)
                .prettyPrint();
        JsonElement parsed = new JsonParser().parse(created);
        JsonElement id = parsed.getAsJsonObject().get("id");
        return id.toString();
    }

    @Step("Delete a Triangle by id: {0}")
    public static void deleteTriangle(String id){
        message("> a Triangle with id: "+id+" will be deleted");
        Response t =given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .pathParam("triangleId", id)
        .when()
                .delete(TRIANGLE_ENDPOINT + "/{triangleId}");
        Assert.assertEquals(t.getStatusCode(),200);
    }

    @Step("Delete the first Triangle")
    public static void deleteTriangle() throws IOException {
        getTriangles(true);
        message("> a Triangle with id: "+
                vars.getTriangleId().replace("\"","")+" will be deleted");
        Response t =given(requestSpecification)
                .baseUri(BASE_URL_TRIANGLE)
                .log().everything()
                .contentType(ContentType.JSON)
                .pathParam("triangleId", vars.getTriangleId().replace("\"",""))
                .when()
                .delete(TRIANGLE_ENDPOINT + "/{triangleId}");
        Assert.assertEquals(t.getStatusCode(),200);
    }

    public String getFirstTriangleIdFromTheServer() throws IOException {
        Set<Triangles> triangles = getTriangles(true);
        return triangles.toArray()[triangles.size()-1].toString();
    }


}
