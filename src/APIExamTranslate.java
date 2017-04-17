/**
 * Created by danawacomputer on 2017-04-14.
 */

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class APIExamTranslate {
    public static void main(String[] args) throws Exception {

        String clientId = "UAn9Yzxr9pvMwDgwbuC4";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "wVle4V6Y5w";//애플리케이션 클라이언트 시크릿값";

        try {

            BufferedReader brr = new BufferedReader(new FileReader("src\\closer (1).txt"));
            String line = "";

            String newgab = "";

            while ((line = brr.readLine()) !=null) {// 읽다가 공백이 나올 경우를 설정

                if(line.equals("")) continue;

                char last = line.charAt(line.length() - 1);

                if(last != '.'){
                    newgab = newgab + line;
                    continue;
                }

                newgab = line;
                String text = URLEncoder.encode(newgab, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=en&target=ko&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if(responseCode==200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println(response.toString());

                String jsonData = response.toString();

                JSONObject obj = new JSONObject(jsonData);

//                    JSONObject msgObj = obj.getJSONObject("message");//메세지라는 키의 값에 해당되는 것이 object
//                    JSONObject resObj = msgObj.getJSONObject("result");
//                    String result = resObj.getString("translatedText");


                String result =  obj.getJSONObject("message").getJSONObject("result").getString("translatedText");//chaining으로 들어가는 것이 일반적임. 계속 파고들어가서 찾아내는 것
                System.out.println(result);
            }



        } catch (Exception e) {
            System.out.println(e);
        }
    }
}