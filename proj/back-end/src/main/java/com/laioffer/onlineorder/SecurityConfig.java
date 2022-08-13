package com.laioffer.onlineorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;
//这是个configure类，负责得到data access，验证不归他管，那是Filter的事
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{//需要告诉filter到哪找数据进行匹配，取得数据访问权限
    @Autowired
    private DataSource dataSource;//访问数据库的bean
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    //取得访问权限(authentication)，告诉去哪个table里找
    {
        auth//会自动连接上数据库的data source
                .jdbcAuthentication()
                .dataSource(dataSource)//连接上database
                .usersByUsernameQuery("select email, password, enabled from customers where email = ?")
                .authoritiesByUsernameQuery("select email, authorities from authorities where email = ?");//取出数据
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception//负责定义authorization，什么权限可以访问什么内容
    {
        http
                .csrf()
                .disable()//csrf（跨域请求伪造）不用，默认的防csrf攻击是enable的，这里关掉
                .formLogin()
                .failureForwardUrl("/login?error=true");//请求失败后forward到哪里

        http
                .authorizeRequests()
                .antMatchers("/order/*", "/checkout", "/cart")
                .hasAuthority("ROLE_USER")
                .anyRequest()
                .permitAll();//这里的ROLE_USER是CustomerDao里面的ROLE_USER权限等级，你也可以加更多的
    }
    @Bean
    public static NoOpPasswordEncoder passwordEncoder()//取消密码，本项目是通过明文传输，画横线是被deprecated了
    {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}