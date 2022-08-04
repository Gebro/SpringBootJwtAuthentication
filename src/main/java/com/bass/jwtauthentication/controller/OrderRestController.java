package com.bass.jwtauthentication.controller;


import com.bass.jwtauthentication.dto.OrderDto;
import com.bass.jwtauthentication.model.Order;
import com.bass.jwtauthentication.security.services.OrderServiceHImpl;
import com.bass.jwtauthentication.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Primary
@RestController
@RequestMapping("api")
public class OrderRestController {

    @Autowired
    private OrderServiceHImpl orderService;


    //expose "/orders" and return list of orders
    @GetMapping("/orders/all")
    public ResponseEntity<List<OrderDto>> findAll(){

        return ResponseEntity.ok().body(orderService.findAll());
    }

    @GetMapping(value = "/orders")
    public List<OrderDto> getOrdersDto() {

        //get user id from auth token
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return orderService.getAllOrdersDto(userPrincipal.getId());
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto findById(@PathVariable("orderId") int id){
        return orderService.getOrderByIdDto(id);

    }

//    @GetMapping(value = "/orders/user/{userId}")
//    @ResponseBody
//    public List<OrderDto> getOrdersDto(@PathVariable("userId") int id) {
//        return orderService.getAllOrdersDto(id);
//    }

    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order order) {

        return orderService.saveOrder(order);
    }
    @PutMapping("/orders")
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrderById(@PathVariable("orderId") int id)
    {
        orderService.deleteOrderById(id);
        return "Deleted order id - " +id;
    }

    }

