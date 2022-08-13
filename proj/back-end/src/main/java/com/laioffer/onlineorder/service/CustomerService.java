package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.CustomerDao;
import com.laioffer.onlineorder.entity.Cart;
import com.laioffer.onlineorder.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//表示跟业务逻辑相关的，spring会把包含service的class创建出来
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
//service在中间层，signUp是从controller发过来的请求传给Dao，getCustomer是从Dao发上来的数据返给controller
    public void signUp(Customer customer) {
        Cart cart = new Cart();
        customer.setCart(cart);
        customer.setEnabled(true);

        customerDao.signUp(customer);
    }

    public Customer getCustomer(String email) {
        return customerDao.getCustomer(email);
    }
}

