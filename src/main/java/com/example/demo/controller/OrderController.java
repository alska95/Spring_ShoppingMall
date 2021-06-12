package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.item.Item;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;


    public void register(Member member , Item item , int count){
        orderService.order(member.getId() , item.getId() , count);
    }
}
