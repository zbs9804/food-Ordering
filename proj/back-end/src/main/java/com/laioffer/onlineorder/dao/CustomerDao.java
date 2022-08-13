package com.laioffer.onlineorder.dao;
import com.laioffer.onlineorder.entity.Authorities;
import com.laioffer.onlineorder.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository//这是spring的annotation，里面也有一个component，专门跟数据库交互
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;//因为这个类需要用hibernate的功能对数据库进行操作，所以要一个sessionFactory的bean

    public void signUp(Customer customer) {//注册就是要保证原子性。可以自己加error code，返回400，user existed啥的
        Authorities authorities = new Authorities();
        authorities.setEmail(customer.getEmail());
        authorities.setAuthorities("ROLE_USER");//后面的安全框架会检查这个自定义的string，给相应的权限

        Session session = null;

        try
        {
            session = sessionFactory.openSession();
            session.beginTransaction();//Transaction适用于保证session操作的atomicity，关系型的数据库都建议把操作原子化
            session.save(authorities);//save就是数据库的insert
            session.save(customer);//因为cuatomer和authorities没有映射关系，所以这样做保证它们的atomicity
            session.getTransaction().commit();//让session开始执行
        }
        catch(Exception e)
        {
            e.printStackTrace();
            if(session != null)//如果session open成功，且抛出了异常，就rollback
                session.getTransaction().rollback();//如果出现异常可以roll back
        }
        finally
        {
            if(session != null)
                session.close();
        }
    }

    public Customer getCustomer(String email) {
        Customer customer = null;
        Session session = null;
        try//也可以简化为try with resource写法
        {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(session != null)
                session.close();
        }
        return customer;
    }
}

