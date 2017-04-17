package service;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class NaverApiAgent {

    public static String CLIENT_ID = "wrdMfQDJFYdOUwWg_71J";//애플리케이션 클라이언트 아이디값";
    public static String CLIENT_SECRET = "CwQptA9N2Z";//애플리케이션 클라이언트 시크릿값";

    public static String searchAndReturnJson(String keyword) {
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
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

            JSONObject obj = new JSONObject(response.toString());
            JSONArray objarr= (JSONArray)obj.get("items");

            for(int i = 0; i < objarr.length(); i++){

                String title = objarr.getJSONObject(i).getString("title");
                String link = objarr.getJSONObject(i).getString("link");
                String description = objarr.getJSONObject(i).getString("description");
                String bloggername = objarr.getJSONObject(i).getString("bloggername");
                String bloggerlink = objarr.getJSONObject(i).getString("bloggerlink");
                String postdate = objarr.getJSONObject(i).getString("postdate");
                // System.out.print(title + link + description + bloggername + bloggerlink + postdate);

            }

            String result = objarr.getJSONObject(2).getString("link");

            return result;

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public static List searchAndReturnJson_2 (String keyword, int changedisplay) {

        List<String> blogName =  new ArrayList<>();

        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="
                                                + text + "&display=" + changedisplay; // json 결과
            System.out.println(apiURL);
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
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

            JSONObject obj = new JSONObject(response.toString());
            JSONArray objarr= (JSONArray)obj.get("items");

            for(int i = 0; i < objarr.length(); i++){

                String title = objarr.getJSONObject(i).getString("title");
                String link = objarr.getJSONObject(i).getString("link");
                String description = objarr.getJSONObject(i).getString("description");
                String bloggername = objarr.getJSONObject(i).getString("bloggername");
                blogName.add(bloggername); // 리스트 목록에 블로그 이름을 삽입!
                String bloggerlink = objarr.getJSONObject(i).getString("bloggerlink");
                String postdate = objarr.getJSONObject(i).getString("postdate");
                System.out.println (i+ "번쨰 블로그" + bloggername + bloggerlink + postdate);

            }

            System.out.println(response.toString());
            return blogName;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static String tranlate(String source, String target, String text) {

        try {
            String reqeustText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
            con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
            // post request
            String postParams =
                    "source=" + source + "&target="
                            + target + "&text=" + reqeustText;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
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
            //3. 결과 JSON을 파싱하여 콘솔에 출력한다.
            JSONObject obj = new JSONObject(response.toString());
            String result = obj.getJSONObject("message")
                    .getJSONObject("result")
                    .getString("translatedText");

            return result;

        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
}
