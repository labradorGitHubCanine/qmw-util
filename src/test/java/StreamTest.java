import com.qmw.util.StringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    private static final List<User> USER_LIST = new ArrayList<>();

    static {
        for (int i = 0; i < 10; i++)
            USER_LIST.add(new User().setId(i).setName(StringUtil.randomLetter(6)));
    }

    public static void main(String[] args) {
        // map: 获取所有user的id转换为数组
        System.out.println(USER_LIST.stream().map(User::getId).collect(Collectors.toList()));
        // filter: 获取所有id为偶数的user
        System.out.println(USER_LIST.stream().filter(e -> e.getId() % 2 == 0).collect(Collectors.toList()));
        // anyMatch: 查询有没有id=5的user
        System.out.println(USER_LIST.stream().anyMatch(e -> e.getId() == 5));
        // allMatch: 是否所有的用户id都小于 20
        System.out.println(USER_LIST.stream().allMatch(e -> e.getId() < 20));
    }

    @Data
    @Accessors(chain = true)
    static class User {
        int id;
        String name;
    }

}
