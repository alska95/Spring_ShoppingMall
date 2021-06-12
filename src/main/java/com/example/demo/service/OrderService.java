package com.example.demo.service;

import com.example.demo.domain.Delivery;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.item.Item;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        MemberRepository memberRepository,
                        ItemRepository itemRepository,
                        OrderItemRepository orderItemRepository,
                        DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
        this.deliveryRepository = deliveryRepository;
    }




    //주문
    public Long order(Long memberId , Long itemId , int count){
        //엔티티 조회
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        //배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        deliveryRepository.save(delivery);

        //주문상품
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.createOrderItem(item, item.getPrice(), count));
        orderItemRepository.save(orderItems);


        /////주문생성
        Order order = Order.createOrder(member , delivery , orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();

    }

    //취소

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId);
        order.cancel();
    }

    //검색

//    public List<Order> findOrders(Ordersearch)
}
