package org.natera.api;

import org.natera.api.actions.triangles.TrianglesActions;
import org.natera.core.testData.TestVariables;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static org.natera.core.helpers.ApiHelper.message;

public class TestBase {
    TrianglesActions actions = new TrianglesActions();
    TestVariables vars = new TestVariables();
    @BeforeSuite(alwaysRun = true)
    public void checkTrianglesCount() throws IOException {
        vars.setTrianglesSet(actions.getTriangles(true));
        vars.setTriangleCount(vars.getTrianglesSet().size());

    }

    public void skipTest() throws SkipException {
        message("Skip test");
        throw new SkipException("Skip test");
    }
}
