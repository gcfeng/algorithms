package string;

import org.junit.Test;

import java.util.Map;

public class ACTest {
    @Test
    public void test1() {
        // TODO：he会被she覆盖，导致不能匹配出来he
        String patterns[] = {"he", "she", "hers", "his"};
        String text = "ahishers";
        Map<Integer, String> map = AC.match(text, patterns);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test
    public void test2() {
        String[] patterns = {"at", "art", "oars", "soar"};
        String text = "soarsoars";
        Map<Integer, String> map = AC.match(text, patterns);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
