package endPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
    public static Response createUser(String payload)
    {
        RestAssured.baseURI=Routes.base_uri;
        Response response=RestAssured.
        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
        when().post(Routes.post_basePath);
        return response;
    }
    public static Response readUser(String userName)
    {
        RestAssured.baseURI=Routes.base_uri;
            Response response=RestAssured.
        given().pathParam("username",userName).
          when().get(Routes.get_basePath);
        return response;
    }
    public static Response updateUser(String userName,String payload)
    {
        RestAssured.baseURI=Routes.base_uri;
                Response response=RestAssured.
        given().contentType(ContentType.JSON).accept(ContentType.JSON).
        pathParam("username",userName).body(payload).
               when().put(Routes.update_basePath);
        return response;
    }
    public static Response deleteUser(String userName)
    {
        RestAssured.baseURI=Routes.base_uri;
             Response response=RestAssured.
        given().pathParam("username",userName).
        when().delete(Routes.delete_basePath);
        return response;

    }

}


