package biz.init;

import biz.security.JWTAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static biz.common.JWTProp.JWT_VALID_TIME;

@Controller
public class HomeController {

    @Autowired
    JWTAuthentication jwtAuthentication;

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/init")
    @ResponseBody
    public Map<String, Object> init(){
        // API Connection Test
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", "Success");
        result.put("msg", "Connection Success");
        return Map.of("data", result);
    }

    @RequestMapping("/createToken")
    @ResponseBody
    public Map<String, Object> generateToken(String subject) {
        // 유효시간 20분
        logger.debug("토큰 생성하자!!!!!!!!!");
        String token = jwtAuthentication.createToken(subject, JWT_VALID_TIME);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @RequestMapping("/getToken")
    @ResponseBody
    public String getSubject(@RequestHeader String Authorization) {
        String subject = jwtAuthentication.getSubject(Authorization);
        return subject;
    }


}
