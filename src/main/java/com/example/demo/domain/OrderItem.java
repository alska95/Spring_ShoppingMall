package com.example.demo.domain;

import com.example.demo.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//다대일 사이에 껴있음.
@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격
    private int count; //수량

    //orderitem 생성

    public static OrderItem createOrderItem(Item item , int orderPrice , int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);

        item.removeStock(count);
        return orderItem;
    }
    public void cancel(){
        getItem().addStock(count);
    }

    public int getTotalPrice(){
        return getOrderPrice()*getCount();
    }

}
