import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author wpw
 * @since 2022/5/4
 */
public class test {
    public static void main(String[] args) {
        System.out.println(longestPalindromeSubseq("bbbab"));
    }

    public static int longestPalindromeSubseq(String s) {
        int len = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < len; ++i) {
            if (map.get(s.charAt(i)) != null) {
                map.put(s.charAt(i), map.get(s.charAt(i) + 1));
            } else {
                map.put(s.charAt(i), 1);
            }
        }
        int result = 0;
        for (Integer value : map.values()) {
            if (value != null) {
                if (value % 2 == 0) {
                    result += value;
                } else {
                    result = result + value - 1;
                }
            }
        }
        return result;
    }
}
