package com.laioffer.onlineorder.dao;
//通过hibernate和database进行交互拿到数据
import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuInfoDao {//dao时在中间层，负责接受service的请求，然后去DB里找数据，最后返回给controller
    @Autowired
    private SessionFactory sessionFactory;//这个SessionFactory在ApplicationConfig里

    public List<Restaurant> getRestaurants()
    {
        Session session = null;

        try
        {
            session = sessionFactory.openSession();//工厂模型
            CriteriaBuilder builder = session.getCriteriaBuilder();//定义对表的搜索条件，拿到controller的请求后会自动去做query
            CriteriaQuery<Restaurant> criteriaQuery = builder.createQuery(Restaurant.class);
            criteriaQuery.from(Restaurant.class);//from就是select* from Restaurant的意思，.class是因为这个API的参数要传class
            return session.createQuery(criteriaQuery).getResultList();
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
        return new ArrayList<>();
    }

    public List<MenuItem> getAllMenuItem(int restaurantId)//通过restaurant id找到restaurant的所有菜
    {//从Restaurant类里拿菜单List
        Session session = null;
        try{
            session = sessionFactory.openSession();
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);//get时hibernate的方法，根据主键搜索
            if(restaurant != null)
                return restaurant.getMenuItemList();
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
        return new ArrayList<>();
    }

    public MenuItem getMenuItem(int menuItemId)//往购物车里添加菜的时候，call这个函数返回一个item
    {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MenuItem.class, menuItemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}