package org.natera.api.checkPoints;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.natera.api.classes.Triangles;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

import static org.natera.core.helpers.ApiHelper.message;

public class TrianglesCheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Check that the Triangle with id: {1} exists on the server")
    public void checkThatCreatedTriangleIdExistsInTheList(Set<Triangles> triangles, String triangleId){
        message("> check that the Triangle with id: "+triangleId+" exists on the server");
        String checkState ="doesn't";
        for(Triangles triangle:triangles){
            if(triangle.getId().contains(triangleId)){
                checkState="contains";
                break;
            }
        }
        softAssert.assertEquals(checkState,"contains","triangle with id: "+triangleId+
                " wasn't found in triangles list");
        softAssert.assertAll();
    }

    @Step("Check that the Triangle with id: {1} doesn't exist on the server")
    public void checkThatCreatedTriangleWithIdDoesntExistInTheList(Set<Triangles> triangles, String triangleId){
        message("> check that the Triangle with id: "+triangleId+" doesn't exist on the server");
        String checkState ="doesn't";
        for(Triangles triangle:triangles){
            if(triangle.getId().contains(triangleId))checkState="contains";
        }
        softAssert.assertEquals(checkState,"doesn't","triangle with id: "+triangleId+
                " was found in triangles list");
        softAssert.assertAll();
    }

    @Step("Check that amount of triangles on the server is changed new value: {0}, old value: {1}")
    public void checkTrianglesCountOnTheServer(int newCount, int oldCount, String errorMessage){
        message("> check that amount of triangles on the server is changed new value: "
                + newCount+", old value: "+ oldCount);
        Assert.assertNotEquals(newCount,oldCount,errorMessage);
    }

    @Step("Check that amount of triangles on the server wasn't changed new value: {0}, old value: {1}")
    public void checkTrianglesCountOnTheServerNotChanged(int newCount, int oldCount, String errorMessage){
        message("> check that amount of triangles on the server wasn't changed new value: "
                + newCount+", old value: "+ oldCount);
        Assert.assertEquals(newCount,oldCount,errorMessage);
    }

    @Step("Check that amount of triangles on the server {0} equals to the limit: {1}")
    public void checkTrianglesCountLessThanLimit(int newCount,int limit, String errorMessage){
        message("> check that amount of triangles on the server: "+newCount+" less than eleven");
        Assert.assertEquals(newCount,limit,errorMessage);
    }

    @Step("Check that the response code {1} equals to expected value")
    public void checkResponseCode(Response resp,int code){
        message("> check that the response code: "+code+" equals to expected value");
        Assert.assertEquals(resp.getStatusCode(),code,
                "The status code doesn't correspond to expected value");
    }
    @Step("{1}")
    public void checkBoolResult(Boolean result,String message){
        message(message);
        Assert.assertTrue(result,"it was possible to create more triangles than limit");
    }
}
