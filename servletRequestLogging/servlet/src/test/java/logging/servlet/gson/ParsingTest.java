package logging.servlet.gson;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@SpringBootTest
public class ParsingTest {

    @Test
    public void parsingTest() {

        List<Integer> list1 = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> list2 = new ArrayList<>(List.of(5, 6));

        boolean b = list1.stream()
                .anyMatch(list2::contains);
        System.out.println(b);


    }
}
