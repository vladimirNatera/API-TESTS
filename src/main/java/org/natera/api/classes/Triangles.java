package org.natera.api.classes;

public class Triangles {
    private  String id;
    private  Integer firstSide;
    private  Integer secondSide;
    private  Integer thirdSide;

    public String getId() {
        return id;
    }

    public Integer getFirstSide() {
        return firstSide;
    }

    public Integer getSecondSide() {
        return secondSide;
    }

    public Integer getThirdSide() {
        return thirdSide;
    }

    public Triangles withId(String id) {
        this.id = id;
        return this;
    }

    public Triangles withFirstSide(Integer firstSide) {
        this.firstSide = firstSide;
        return this;
    }

    public Triangles withSecondSide(Integer secondSide) {
        this.secondSide = secondSide;
        return this;
    }

    public Triangles withThirdSide(Integer thirdSide) {
        this.thirdSide = thirdSide;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangles triangles = (Triangles) o;

        if (id != null ? !id.equals(triangles.id) : triangles.id != null) return false;
        if (firstSide != null ? !firstSide.equals(triangles.firstSide) : triangles.firstSide != null) return false;
        if (secondSide != null ? !secondSide.equals(triangles.secondSide) : triangles.secondSide != null) return false;
        return thirdSide != null ? thirdSide.equals(triangles.thirdSide) : triangles.thirdSide == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstSide != null ? firstSide.hashCode() : 0);
        result = 31 * result + (secondSide != null ? secondSide.hashCode() : 0);
        result = 31 * result + (thirdSide != null ? thirdSide.hashCode() : 0);
        return result;
    }
}
