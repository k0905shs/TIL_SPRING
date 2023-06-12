package hello.security.testController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/*")
public class TestController {

    @GetMapping("v1")
    public String requestTest1() {
        return "ok";
    }


}
