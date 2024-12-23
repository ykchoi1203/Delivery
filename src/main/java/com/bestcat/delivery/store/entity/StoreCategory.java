package com.bestcat.delivery.store.entity;

import com.bestcat.delivery.category.entity.Category;
import com.bestcat.delivery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_category")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public StoreCategory(Store store, Category category) {
        this.store = store;
        this.category = category;
    }
}
