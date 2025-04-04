package com.inspire12.likelionbackend.module.jpa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionbackend.module.jpa.model.dto.OrderSum;
import com.inspire12.likelionbackend.module.jpa.model.entity.OrderEntity;
import com.inspire12.likelionbackend.module.jpa.model.mapper.OrderMapper;
import com.inspire12.likelionbackend.module.jpa.model.request.OrderRequest;
import com.inspire12.likelionbackend.module.jpa.model.response.OrderListResponse;
import com.inspire12.likelionbackend.module.jpa.model.response.OrderResponse;
import com.inspire12.likelionbackend.module.jpa.model.response.OrderSumResponse;
import com.inspire12.likelionbackend.module.jpa.model.response.OrderSummaryResponse;
import com.inspire12.likelionbackend.module.jpa.repository.OrderJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.inspire12.likelionbackend.module.jpa.model.mapper.OrderMapper.fromEntity;
import static com.inspire12.likelionbackend.module.jpa.model.mapper.OrderMapper.toEntity;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;


    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        OrderEntity order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 없음"));
        return fromEntity(order);
    }

    @Transactional
    public OrderResponse saveOrder(OrderRequest request) {
        OrderEntity order = toEntity(request);
        OrderEntity savedOrder = orderJpaRepository.save(order);
        return fromEntity(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 없음"));
        orderJpaRepository.delete(order);
    }

    @Transactional
    public OrderResponse updateTotalAmount(Long orderId, Integer newAmount) {
        OrderEntity order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 없음"));
        order.changeTotalAmount(newAmount);
        return fromEntity(order);
    }


    // 고객 ID로 주문 요약 정보 조회
    @Transactional(readOnly = true)
    public OrderSummaryResponse getOrderSummaries(Long customerId) {
        return new OrderSummaryResponse(orderJpaRepository.findOrderSummariesByCustomerId(customerId));
    }


    public OrderSumResponse getOrderSum(Long customerId) {
        OrderSum orderSum = orderJpaRepository.sumAmountByUserId(customerId);
        return new OrderSumResponse(orderSum.getCustomerId(), orderSum.getCount());
    }

    public OrderListResponse getOrderByPager(Pageable pageable) {
        Page<OrderEntity> all = orderJpaRepository.findAll(pageable);

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderEntity orderEntity : all) {
            orderResponses.add(fromEntity(orderEntity));
        }
        return new OrderListResponse(orderResponses);
    }

    public OrderListResponse getOrderItemsByPager(Pageable pageable) {;
        Page<OrderEntity> all = orderJpaRepository.findAllOrders(pageable);

        System.out.println("log123: " + all.getTotalElements());

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderEntity orderEntity : all) {
            orderResponses.add(fromEntity(orderEntity));
        }


//        List<OrderResponse> orderResponses = all.stream().map(OrderMapper:: fromEntity).collect(Collectors.toList());
        System.out.println("log123: " + orderResponses.size());

        return new OrderListResponse(orderResponses);
    }
}