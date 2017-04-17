/**
 * Created by danawacomputer on 2017-04-17.
 */
import service.NaverApiAgent;

import java.util.ArrayList;
import java.util.List;

public class NaverSearch {
    public static void main(String[] args) {

        String keyword = "자바라";
        int changedisplay = 20;

        String json = NaverApiAgent.searchAndReturnJson(keyword); // 3번 째에 있는 링크값 출력하기
        List<String> blogName = NaverApiAgent.searchAndReturnJson_2(keyword, changedisplay);
                                    // 검색값을 n개로 주고 이들의 blogname을 가져옴

        System.out.println(json);

        for(String e : blogName){
            System.out.println(e);
        }

        // System.out.println(blogName); //

    }
}
