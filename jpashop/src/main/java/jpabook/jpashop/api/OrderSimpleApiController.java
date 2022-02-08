package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.SimpleOrderQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 주문 조회
 * Order 조회
 * Order -> Member 조회
 * Order -> Delivery 조회
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> findOrders = orderRepository.findAll(new OrderSearch());
        return findOrders;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderQueryDto> result = orders.stream()
                .map(SimpleOrderQueryDto::new)
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderQueryDto> result = orders.stream()
                .map(SimpleOrderQueryDto::new)
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<SimpleOrderQueryDto> result = orderRepository.findOrderDtos();
        return new Result(result.size(), result);
    }

    @Data
    @AllArgsConstructor
    public static class Result<T> {
        int count;
        T data;
    }
}
