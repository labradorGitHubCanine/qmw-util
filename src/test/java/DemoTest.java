public class DemoTest {

    public static int lengthOfLongestSubstring(String s) {
        int index = 1, count = 0;
        if (!s.isEmpty()) {
            int ch = s.charAt(0);
            for (int i = 1; i < s.toCharArray().length; i++) {
                if (ch == s.charAt(i)) {
                    index = 1;
                } else {
                    index++;
                }
                count = Math.max(count, index);
                ch = s.charAt(i);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    static class A {

    }

    static class B extends A {

    }

}
