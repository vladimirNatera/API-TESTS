package org.natera.api;


public class Constants {
    public static String BASE_URL_TRIANGLE = "https://qa-quiz.natera.com";
    public static String USER_TOKEN ="a62efb5b-7e8c-4f82-9fb3-3b63135eb6d9";
    public static int TRIANGLES_LIMIT =10;

    //public static boolean restIgnoreProperties = true;
    public String PERIMETER_ENDPOINT = BASE_URL_TRIANGLE + "/{triangleId}/perimeter";
    public String AREA_ENDPOINT = BASE_URL_TRIANGLE + "/{triangleId}/area";
}
