package test;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import endPoints.UserEndPoints;
import Payload.User;
import io.restassured.response.Response;

public class TestEndPoints {
    Faker faker;
    User userPayload;
    @BeforeTest
    public void beforeTest()
    {
        System.out.println("UserName Under Test");
        faker=new Faker();
        userPayload=new User();
        userPayload.setUserId(faker.idNumber().hashCode());
        userPayload.setUserName(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        System.out.println("Some Random username  by Faker:\t"+userPayload.getUserName());
    }
    @Test(priority = 1)
    public void testPostUser() {
        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("id", userPayload.getUserId());
        bodyParams.put("username", userPayload.getUserName());
        bodyParams.put("firstName", userPayload.getFirstName());
        bodyParams.put("lastName", userPayload.getLastName());
        bodyParams.put("email", userPayload.getEmail());
        bodyParams.put("password", userPayload.getPassword());
        bodyParams.put("phone", userPayload.getPhone());
        bodyParams.put("userStatus", 0);
        String payload = new Gson().toJson(bodyParams);
        System.out.println("-----POST-------");
        Response response = UserEndPoints.createUser(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        System.out.println("-----" + userPayload.getUserName() + " is created -------");
    }

    @Test(priority = 2)
    public void testGetUserByName()

    {
        System.out.println("-------Get---------");
              Response response=UserEndPoints.readUser(this.userPayload.getUserName());
          response.then().log().body().statusCode(200);
        System.out.println("--------- "+this.userPayload.getUserName()+"----------");
    }
    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        Map<String,Object> bodyParams=new HashMap<String,Object>();
        bodyParams.put("id",userPayload.getUserId());
        bodyParams.put("username",userPayload.getUserName());
        bodyParams.put("firstName",userPayload.getFirstName()+" is my first name");
        bodyParams.put("lastName",userPayload.getLastName()+" is my last name");
        bodyParams.put("email",userPayload.getEmail()+" is my email");
        bodyParams.put("password",userPayload.getPassword()+" is my password");
        bodyParams.put("phone",userPayload.getPhone()+" is my phone number");
        bodyParams.put("userStatus",1);
        String payload=new Gson().toJson(bodyParams);

        System.out.println("-------Updated---------");
        Response response=UserEndPoints.updateUser(this.userPayload.getUserName(), payload);
        response.then().log().body().statusCode(200);
        Response afterUpdateResponse=UserEndPoints.readUser(this.userPayload.getUserName());
        afterUpdateResponse.then().log().body().statusCode(200);
        System.out.println("---------  "+this.userPayload.getUserName()+" is updated ----------");
    }
    @Test(priority = 4)
    public void testDeleteUserByName()
    {

        System.out.println("----Delete User-----");
        Response response=UserEndPoints.deleteUser(this.userPayload.getUserName());
        response.then().log().body().statusCode(200);
        System.out.println("------ "+this.userPayload.getUserName()+" is deleted ---------");

    }

}