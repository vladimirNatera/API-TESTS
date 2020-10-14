package org.natera.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.NotImplementedException;
import org.natera.api.actions.triangles.TrianglesActions;
import org.natera.api.checkPoints.TrianglesCheckpoints;
import org.natera.api.classes.Triangles;
import org.natera.core.helpers.ConsoleOutputCapturerHelper;
import org.natera.core.testData.TestVariables;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;


public class TriangleTests extends TestBase {
    ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    Triangles triangle = new Triangles();
    TrianglesActions trianglesActions = new TrianglesActions();

    TestVariables vars = new TestVariables();
    TrianglesCheckpoints trianglesCheckpoints = new TrianglesCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Start capturing system.out logs")
    public void quickLogout() {
        capture.start();
    }
    @AfterMethod(alwaysRun = true,
            description ="Adding captured logs to Allure")
    public void afterTest() throws IOException {
        String value = capture.stop();
        Allure.addAttachment("system.out logs",value);
    }

    @Test(description = "Test delete a triangle if it's exists " +
            "and create and delete if there are no triangles", priority = 1)
    public void testDeleteATriangle() throws IOException {
        if(vars.getTriangleId()==null)vars.setTriangleId(trianglesActions.createTriangle());
        trianglesActions.deleteTriangle(vars.getTriangleId().replace("\"",""));
        Set<Triangles> newTriangles = trianglesActions.getTriangles(false);
        vars.setTrianglesSet(newTriangles);
        trianglesCheckpoints.checkThatCreatedTriangleWithIdDoesntExistInTheList(newTriangles,
                vars.getTriangleId().replace("\"",""));
    }

    @Test(description = "Test creates a triangle and checks that it was added to the server", priority = 2)
    public void testCreateTriangle() throws IOException {
        if(vars.getTriangleCount()>Constants.TRIANGLES_LIMIT)
            throw new SkipException("Skipping this test because of the bug on the server related to the Triangles limit");
        if(vars.getTriangleCount()==Constants.TRIANGLES_LIMIT){
            trianglesActions.deleteTriangle();
            vars.setTrianglesSet(trianglesActions.getTriangles(false));
        }
        Set<Triangles> oldTriangles = trianglesActions.getTriangles(false);
        String triangleId = trianglesActions.createTriangle();
        Set<Triangles> newTriangles = trianglesActions.getTriangles(false);
        trianglesCheckpoints.checkTrianglesCountOnTheServer(newTriangles.size(),
                oldTriangles.size(),"Triangles amount on the server wasn't changed");
        trianglesCheckpoints.checkThatCreatedTriangleIdExistsInTheList(newTriangles,
                triangleId.replace("\"",""));
    }

    @Test(description = "Test creates a triangle with zeros sides and checks that" +
            " it wasn't added to the server", priority = 3)
    public void testCreateTriangleWithZeros() throws IOException {
        if(vars.getTriangleCount()>Constants.TRIANGLES_LIMIT)
            throw new SkipException("Skipping this test because of the bug on the server related to the Triangles limit");
        if(vars.getTriangleCount()==Constants.TRIANGLES_LIMIT){
            trianglesActions.deleteTriangle();
            vars.setTrianglesSet(trianglesActions.getTriangles(false));
        }
        Set<Triangles> oldTriangles = trianglesActions.getTriangles(false);

        String triangleId = trianglesActions.createTriangle(0,0,0);
        Set<Triangles> newTriangles = trianglesActions.getTriangles(false);
        trianglesCheckpoints.checkTrianglesCountOnTheServerNotChanged(newTriangles.size(),
                oldTriangles.size(),"Triangles amount on the server was changed triangles" +
                        " with zeros was added");
        trianglesCheckpoints.checkThatCreatedTriangleWithIdDoesntExistInTheList(newTriangles,
                triangleId.replace("\"",""));
    }

    @Test(description = "Test tries to create more triangles than set limit on the server", priority = 6)
    public void testCreateTrianglesMoreThanLimit() throws IOException {
        try{
            vars.setTrianglesSet(trianglesActions.getTriangles(false));
            if(vars.getTriangleCount()>Constants.TRIANGLES_LIMIT){
                trianglesCheckpoints.checkBoolResult(false,
                        "Triangles limit already exceeded on the server the current limit is: "
                                +Constants.TRIANGLES_LIMIT);
            }
            else if(vars.getTriangleCount()==Constants.TRIANGLES_LIMIT){
                trianglesActions.deleteTriangle();
                vars.setTrianglesSet(trianglesActions.getTriangles(false));
            }
            for (int i=vars.getTrianglesSet().size();i<Constants.TRIANGLES_LIMIT+1;i++){
                trianglesActions.createTriangle();
            }
            Set<Triangles> newTriangles = trianglesActions.getTriangles(false);
            if(newTriangles.size()>Constants.TRIANGLES_LIMIT){
                trianglesActions.deleteTriangle();
                trianglesCheckpoints.checkTrianglesCountLessThanLimit(newTriangles.size(),
                        Constants.TRIANGLES_LIMIT," Amount of triangles exceeds the limit");
            }
        }catch (Exception ex){
            vars.setTrianglesSet(trianglesActions.getTriangles(false));
            trianglesCheckpoints.checkTrianglesCountLessThanLimit(vars.getTrianglesSet().size(),
                    Constants.TRIANGLES_LIMIT," Amount of triangles exceeds the limit");
        }
    }

    @Test(description = "Test checks that request /triangles/all works", priority = 4)
    public void testGetAllTriangles(){
        Response resp = trianglesActions.getTrianglesAll();
        trianglesCheckpoints.checkResponseCode(resp,200);
    }

    @Test(description = "Test tries to execute request /triangles/all by not unauthorized user", priority = 5)
    public void testGetAllTrianglesUnauthorized(){
        Response resp = trianglesActions.getTrianglesUnauthorized();
        trianglesCheckpoints.checkResponseCode(resp,401);
    }

    @Test (description = "Test checks that calculation of the perimeter on the server is correct",
    priority = 7)
    public void testChecksThePerimeter(){
        throw new NotImplementedException("Test is not implemented yet");
    }

    @Test (description = "Test checks that calculation of the area on the server is correct",
            priority = 8)
    public void testChecksTheArea(){
        throw new NotImplementedException("Test is not implemented yet");
    }









}
