package com.laioffer.onlineorder;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import com.laioffer.onlineorder.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
//这对应的是网址后面加/hello-servlet的网页，tomcat优先检测index.html，然后是index.jsp，两个是默认主页，在默认主页后面加/hello-servlet
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*//request就是tomcat帮你把前端传进来的请求整个成的一个object，response就是要respond的内容，tomcat会帮你返回给前端
        response.setContentType("text/html");///data type("text/plain") ("application/json")

        // Hello
        String customer = request.getParameter("customer");//customer是网址参数的名字，比如localhost:8080/hello-servlet?customer=baishan
        PrintWriter out = response.getWriter();
        out.println("<html><body>");//这就是html的格式，<>就是标签
        out.println("<HelLo>" + customer + "</h1>");
        out.println("</body></html>");*/

        /*json
        response.setContentType("application/json");
        JSONObject object = new JSONObject();
        PrintWriter out = response.getWriter();
        object.put("email", "sun@lsioffer.com");
        object.put("name","rick sun");
        object.put("age",45);
        out.println(object);*/

        //Jackson
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        Customer customer= new Customer();
        customer.setEmail("sun@laioffer.com");
        customer.setPassword("123456");
        customer.setFirstName("rick");
        customer.setLastName("sun");
        customer.setEnabled(true);
        response.getWriter().print(mapper.writeValueAsString(customer));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //receive data
        response.setContentType("application/json");
        JSONObject objIn = new JSONObject(IOUtils.toString(request.getReader()));
        String eml = objIn.getString("email");
        String lstName = objIn.getString("last_name");
        int age = objIn.getInt("age");
        System.out.println(eml+lstName+age);

        //response status after receive data
        response.setContentType("application/json");
        JSONObject objOut = new JSONObject();
        objOut.put("status","okk");
        response.getWriter().print(objOut);
    }
    public void destroy() {
    }
}