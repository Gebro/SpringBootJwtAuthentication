package com.bass.jwtauthentication.repository;


import com.bass.jwtauthentication.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService extends JpaRepository<Order,Integer> {

    List<Order> findByUserId(long user_id);

}
