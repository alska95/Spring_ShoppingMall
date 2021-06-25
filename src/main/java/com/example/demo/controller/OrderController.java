package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.OrderSearch;
import com.example.demo.service.ItemService;
import com.example.demo.service.MemberService;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members" , members);
        model.addAttribute("items" , items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(
            @RequestParam("memberId") Long memberId ,
            @RequestParam("itemId")Long itemId ,
            @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    public void register(Member member , Item item , int count){
        orderService.order(member.getId() , item.getId() , count);
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders" , orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancle")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
