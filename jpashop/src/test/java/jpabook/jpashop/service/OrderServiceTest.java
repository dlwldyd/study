package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;

    @Test
    public void order() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "한강", "1111"));
        em.persist(member);

        Book book = new Book();
        book.setName("spring");
        book.setPrice(10000);
        int bookQuantity = 10;
        book.setStockQuantity(bookQuantity);
        em.persist(book);


        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(bookQuantity - orderCount);
    }

    @Test
    public void orderCancel() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "한강", "1111"));
        em.persist(member);

        Book book = new Book();
        book.setName("spring");
        book.setPrice(10000);
        int bookQuantity = 10;
        book.setStockQuantity(bookQuantity);
        em.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        orderService.cancelOrder(orderId);


        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(bookQuantity);
    }

    @Test
    public void stockExceed() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "한강", "1111"));
        em.persist(member);

        Book book = new Book();
        book.setName("spring");
        book.setPrice(10000);
        int bookQuantity = 10;
        book.setStockQuantity(bookQuantity);
        em.persist(book);


        int orderCount = 20;

        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }
}