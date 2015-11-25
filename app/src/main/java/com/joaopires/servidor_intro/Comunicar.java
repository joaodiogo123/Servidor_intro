package com.joaopires.servidor_intro;
//Comunicar com servidor remoto
//Saca tudo do HTTP

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Comunicar {
    public static String contactar(String host, int port, String path) throws IOException {
        URL url = new URL("http", host, port, path);
        Log.w("Host: ", host);
        Log.w("Port: ", Integer.toString(port));
        Log.w("Path: ", path);

        URLConnection conn = url.openConnection();
        // This does a GET; to do a POST, add conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setAllowUserInteraction(true); // useless but harmless
        conn.connect();
        // To do a POST, you'd write to conn.getOutputStream());
        StringBuilder sb = new StringBuilder(); //Uma string que pode ser alterada
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) { //Ler linha a linha do buffer
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static String contactar2(String host, String path, int port) {
        String resultado = "<unknown>";
        //String host = "androidcookbook.net";
        //String path = "/seam/resource/rest/recipe/list";
        try {
            resultado = contactar(host, port, path);
        } catch (IOException e) {
            resultado = e.toString(); //Ver qual foi a exception
        }
        return resultado;
    }
}