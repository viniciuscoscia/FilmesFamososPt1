package com.example.viniciuscoscia.filmesfamosos.utils;

public class Utils {
    public static String formatarData(String data){
        return data.split("-")[2] + "/" + data.split("-")[1]  + "/" + data.split("-")[0];
    }
}
