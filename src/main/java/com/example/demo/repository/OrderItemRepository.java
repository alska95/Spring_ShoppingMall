package com.example.demo.repository;

import com.example.demo.domain.OrderItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(List<OrderItem> orderItems){
        for(OrderItem orderItem : orderItems)
            em.persist(orderItem);
    }
}
