package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemOrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(OrderItem orderItem) {//service会call这个save方法，然后这个save方法会把购物车存到DB里
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();//写数据库的操作都要开transaction，避免竞争
            session.save(orderItem);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) session.getTransaction().rollback();//catch里还要加rollback
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

