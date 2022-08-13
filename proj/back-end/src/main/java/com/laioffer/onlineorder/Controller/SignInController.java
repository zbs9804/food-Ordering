package com.laioffer.onlineorder.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignInController {

    private ObjectMapper objMapper = new ObjectMapper();

    @RequestMapping("/login")
    public void login(@RequestParam(value = "error")String error, HttpServletResponse response) throws IOException
    //登录失败时：这里的error对应securityConfig里的/login ? error = true
    {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("message", "bad credential");//value就是告诉前端登录失败
        response.getOutputStream().println(objMapper.writeValueAsString(data));
    }
}
