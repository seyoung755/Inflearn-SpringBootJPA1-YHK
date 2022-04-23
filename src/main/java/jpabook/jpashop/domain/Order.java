package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL) // cascade 를 통하면 order를 저장할 때 자동으로 delivery도 저장이 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // Order를 중심으로 Delivery를 조회하는 경우가 많기 때문에 Order가 FK를 가지고 연관관계의 주인이 됨.

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태 [ORDER, CANCEL]

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); // 멤버를 설정할 때 멤버쪽에도 자동으로 반영되도록
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this); // orderItem을 등록할 때 그쪽에도 반영되도록
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
