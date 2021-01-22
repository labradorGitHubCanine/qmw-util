import com.qmw.util.StringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User().setId(i).setName(StringUtil.randomLetter(5)));
        }
        System.out.println(list.stream().map(User::getId).collect(Collectors.toList()));
    }

    @Data
    @Accessors(chain = true)
    static class User {
        int id;
        String name;
    }

}
