package hello.toy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{


    @Override
//    @LogTrace
    public String test() throws Exception {
        Thread.sleep(500);
        return "ok";
    }
}
