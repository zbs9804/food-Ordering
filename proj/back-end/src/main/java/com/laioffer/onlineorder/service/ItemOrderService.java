package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.ItemOrderDao;
import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderService {//service的控制信号来自controller发过来的id，去OrderItem来里看需要填那些fields

    @Autowired
    private ItemOrderDao itemOrderDao;

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private CustomerService customerService;

    public void saveOrderItem(int menuItemId)
    {
        final OrderItem orderItem = new OrderItem();
        final MenuItem menuItem = menuInfoService.getMenuItem(menuItemId);

        Authentication logInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = logInUser.getName();
        Customer customer = customerService.getCustomer(email);
        orderItem.setMenuItem(menuItem);
        orderItem.setCart(customer.getCart());
        orderItem.setPrice(menuItem.getPrice());
        orderItem.setQuantity(1);
        itemOrderDao.save(orderItem);
    }
}