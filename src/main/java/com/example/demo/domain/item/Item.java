package com.example.demo.domain.item;

import com.example.demo.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
@Table(name = "items")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //재고 +-
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }
    public void removeStock(int quantity){
        int restStock = stockQuantity-quantity;
        if(restStock < 0){
            throw new IllegalStateException("제고 부족!");
        }
        this.stockQuantity= restStock;
    }
}