import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by danawacomputer on 2017-04-17.
 */
public class CloserAutoTranslator {
    public static void main(String[] args) throws Exception {

        String clientId = "UAn9Yzxr9pvMwDgwbuC4";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "wVle4V6Y5w";//애플리케이션 클라이언트 시크릿값";

        try {

            BufferedReader brr = new BufferedReader(new FileReader("src\\closer (1).txt"));
            String line ;

            String newgab = "";

            while ((line = brr.readLine()) !=null) {

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

                StringBuffer response = new StringBuffer();
                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                String jsonData = response.toString();

                JSONObject obj = new JSONObject(jsonData);

                String result =  obj.getJSONObject("message")
                                    .getJSONObject("result")
                                    .getString("translatedText");

                System.out.println(result);
            }



        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
