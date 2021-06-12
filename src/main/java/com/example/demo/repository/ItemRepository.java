package com.example.demo.repository;

import com.example.demo.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Item item){
        if(item.getId() == null)
            em.persist(item);
        else{
            em.merge(item); //db에 등록된것을 어디서 가져온경우
        }
    }

    public Item findById(Long id){
        return em.find(Item.class , id);
    }

    public List<Item> findbyName(String _name){
        return em.createQuery(
                "select i from Item i where i.name =: name",Item.class
        ).setParameter("name" , _name).getResultList();
    }

    public List<Item> findAll(){
        return em.createQuery(
                "select i from Item i", Item.class
        ).getResultList();
    }
}
