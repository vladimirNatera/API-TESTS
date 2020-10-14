package org.natera.core.testData;

import org.natera.api.classes.Triangles;
import org.natera.core.settings.Config;

import java.util.Set;

public class TestVariables {
    private static int triangleCount;
    private static String triangleId;
    private static Set<Triangles> trianglesSet;

    public Set<Triangles> getTrianglesSet() {
        return trianglesSet;
    }

    public void setTrianglesSet(Set<Triangles> newTrianglesSet) {
        TestVariables.trianglesSet = newTrianglesSet;
    }

    public String getTriangleId() {
        return triangleId;
    }

    public void setTriangleId(String newTriangleId) {
        this.triangleId = newTriangleId;
    }

    public int getTriangleCount() {
        return triangleCount;
    }

    public void setTriangleCount(int newtriangleCount) {
        this.triangleCount = newtriangleCount;
    }
}
