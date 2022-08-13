package com.laioffer.onlineorder.Controller;

import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller//必须写，用来让dispatch servlet知道我这里有些方法可以处理http request
public class SignUpController
{
    @Autowired
    private CustomerService customerService;
    //根据signup的网址以及method建立mapping，表示signup是个http POST请求，至于如何执行取决于@RequestBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
//    @RequestMapping 是 Spring Web 应用程序中最常被用到的注解之一。这个注解会将 HTTP 请求映射到 MVC 和 REST 控制器的处理方法上。
//
//    Request Mapping 基础用法
//
//    在 Spring MVC 应用程序中，RequestDispatcher (在 Front Controller 之下) 这个 servlet 负责将进入的 HTTP 请求路由到控制器的处理方法。
//
//    在对 Spring MVC 进行的配置的时候, 你需要指定请求与处理方法之间的映射关系
    @ResponseStatus(value = HttpStatus.CREATED)
//@ResponseStatus有code，value，reason三个参数，均非必须，用于指定异常及其原因
    public void signUp(@RequestBody Customer customer)//这个annotation是把前端发送过来的json数据de-serialize成Customer类field
    {
//        @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
//        GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，
//        而是用POST方式进行提交。在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用
//        ，@RequestBody最多只能有一个，而@RequestParam()可以有多个。
        customerService.signUp(customer);
    }
}