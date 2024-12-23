package com.bestcat.delivery.order.entity;

import com.bestcat.delivery.common.entity.BaseEntity;
import com.bestcat.delivery.order.entity.type.OrderStatus;
import com.bestcat.delivery.order.entity.type.OrderType;
import com.bestcat.delivery.store.entity.Store;
import com.bestcat.delivery.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false, nullable = false)
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false, length = 20)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "request_notes")
    private String requestNotes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItems> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItems item) {
        orderItems.add(item);
    }

    public void cancelOrder() {
        this.status = OrderStatus.CANCELLED;
    }

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }
}
