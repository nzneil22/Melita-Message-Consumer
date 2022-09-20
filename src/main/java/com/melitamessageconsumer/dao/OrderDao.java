package com.melitamessageconsumer.dao;


import com.melitamessageconsumer.models.Orders;

import java.util.List;

public interface OrderDao {

    Orders save(Orders client);

    List<Orders> find(String id);
}
