package com.example.demo.repository;

import com.example.demo.domain.Delivery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DeliveryRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Delivery delivery){
        em.persist(delivery);
    }
}
