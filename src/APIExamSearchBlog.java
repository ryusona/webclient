/**
 * Created by danawacomputer on 2017-04-14.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class APIExamSearchBlog {

    public static void main(String[] args) { // 기계번역 해보기

        Scanner in = new Scanner(System.in);
        System.out.print("검색어를 입력하세요 : ");
        String selectName = in.nextLine();
        String clientId = "wrdMfQDJFYdOUwWg_71J";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "CwQptA9N2Z";//애플리케이션 클라이언트 시크릿값";

        try {
            String text = URLEncoder.encode( selectName, "UTF-8"); // s는 검색어
            String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
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
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
