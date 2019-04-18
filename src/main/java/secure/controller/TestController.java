package secure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import secure.domain.Msg;

/**
 * Desciption
 *
 * @author Claire.Chen
 * @create_time 2019 -04 - 03 15:59
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/index")
    public Object testSecurityIndex(Authentication authentication) {
       log.info("auth info:"+authentication.getPrincipal());
        return authentication;
    }

    @RequestMapping("/hello")
    public String testSecurityHome() {
        return "hello";
    }

}
