package com.example.myapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Async extends AsyncTask<String, Void, Void> {
    private StringBuilder urlBuilder, sb;
    private URL url;
    private HttpURLConnection conn;
    private Double asyncLongitude, asyncLatitude;
    private String result;

    @Override
    public Void doInBackground(String... params) {
        MainActivity mainActivity = new MainActivity();
        asyncLongitude = mainActivity.longitude;
        asyncLatitude = mainActivity.latitude;

        String strCoord = String.valueOf(asyncLongitude) + "," + String.valueOf(asyncLatitude);
        // StringBuilder sb = new StringBuilder();

        urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords="
                + strCoord + "&sourcecrs=epsg:4326&output=json&orders=addr"); /* URL */

        try {
            url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "vmq6az5j8u");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "pzU5BCY7QLm7flp918q61QRSYFal29OiXG2lGct0");

            conn.setDoOutput(true); // 쓰기모드 지정
            conn.setDoInput(true); // 읽기모드 지정
            conn.setUseCaches(false); // 캐싱데이터를 받을지 안받을지
            conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

            String strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

            InputStream is = conn.getInputStream(); //input스트림 개방
            OutputStream os = conn.getOutputStream();

            os.write(strCookie.getBytes("euc-kr"));
            StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); //문자열 셋 세팅
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            result = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            String url = "http://example.com/test.jsp";
//            URL obj = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
//
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Content-Type","application/json");
//
//            byte[] outputInBytes = params[0].getBytes("UTF-8");
//            OutputStream os = conn.getOutputStream();
//            os.write( outputInBytes );
//            os.close();
//
//            int retCode = conn.getResponseCode();
//
//            InputStream is = conn.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuffer response = new StringBuffer();
//            while((line = br.readLine()) != null) {
//                response.append(line);
//                response.append("");
//            }
//            br.close();
//
//            String res = response.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
        return null;
    }
}