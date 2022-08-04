package com.bass.jwtauthentication.security.services;


import com.bass.jwtauthentication.dto.OrderDto;
import com.bass.jwtauthentication.model.Order;
import com.bass.jwtauthentication.repository.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceHImpl {

    @Autowired
    OrderService orderService;

    private OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAmount(order.getAmount());
        orderDto.setDate(order.getDate());
        orderDto.setTitle(order.getTitle());
        orderDto.setTotal_price(order.getTotal_price());

        return orderDto;
    }

    public List<OrderDto> findAll() {

        // return the results
        return orderService.findAll().stream().map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderByIdDto(int id) {
        return convertToOrderDto(orderService.findById(id).get());
    }

    public List<OrderDto> getAllOrdersDto(long id) {
        return orderService.findByUserId(id).stream().map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    public Order saveOrder(Order order) {
        return orderService.save(order);
    }

    public Order updateOrder(Order order) {
        Order theOrder = orderService.findById(order.getId()).orElse(null);
        theOrder.setAmount(order.getAmount());
        theOrder.setTitle(order.getTitle());
        theOrder.setTotal_price(order.getTotal_price());
        theOrder.setUser(order.getUser());
        theOrder.setDate(order.getDate());
        return orderService.save(theOrder);
    }
    public void deleteOrderById(int id)
    {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent())
        {
            orderService.deleteById(id);
        } else {
            throw new RuntimeException("No employee record exist for given id");
        }

    }

}
