package com.study.grpc.api.gw.converter;

import com.study.grpc.account.client.Order;
import com.study.grpc.account.client.Orders;
import com.study.grpc.api.gw.dto.OrderView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConverter {

    public OrderView fromDtoToView(Order dto) {
        OrderView order = new OrderView();
        if (dto.hasId()) order.setId(dto.getId());
        order.setBrand(dto.getBrand());
        order.setState(dto.getState());
        order.setPrice(dto.getPrice());
        return order;
    }

    public List<OrderView> fromDtoToView(Orders dtos) {
        return dtos.getOrdersList()
                .stream()
                .map(this::fromDtoToView)
                .toList();
    }
}
