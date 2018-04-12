package com.example.user_pro.androidtestmap;

public class Geoinfo {

    private static Double latigeo;
    private static Double lontigeo;
    private static Integer radius;
    private static Integer success_type;

    public static Double getLatigeo() {
        return latigeo;
    }

    public static void setLatigeo(Double latigeo) {
        Geoinfo.latigeo = latigeo;
    }

    public static Double getLontigeo() {
        return lontigeo;
    }

    public static void setLontigeo(Double lontigeo) {
        Geoinfo.lontigeo = lontigeo;
    }

    public static Integer getRadius() {
        return radius;
    }

    public static void setRadius(Integer radius) {
        Geoinfo.radius = radius;
    }

    public static Integer getSuccess_type() {
        return success_type;
    }

    public static void setSuccess_type(Integer success_type) {
        Geoinfo.success_type = success_type;
    }
}
