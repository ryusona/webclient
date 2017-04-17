import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danawacomputer on 2017-04-14.
 */
public class URLDemo{

    public static void main(String[] args) {


        try {

            URL url = new URL("https://openapi.naver.com/v1/search/blog?query=");
            //URLConnection connn = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); // 주소만 날라가면안되고 헤더값이 필요함

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            List<String> naverlist = new ArrayList<>();

            BufferedReader br = new BufferedReader(isr);

            String tempLine = "";

            while ((tempLine = br.readLine()) != null) {

                String[] split = tempLine.split(" ");

                for (String e : split) {
                    naverlist.add(e);
                }

            }

            int sum =0 ;
            for (String e : naverlist) {
                if(!e.trim().equals("")) sum ++;
            }

            System.out.println(naverlist.size());
            System.out.println(sum);

        } catch (MalformedURLException e) { // exception       (안쓰면 에러가남)
            e.printStackTrace();            // runtimeexception(명시적으로 안줘도 에러가 안남)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
