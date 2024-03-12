package com.study.grpc.api.gw.controller;

import com.study.grpc.api.gw.dto.OrderView;
import com.study.grpc.api.gw.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/{count}")
    public ResponseEntity<List<OrderView>> generateOrders(@PathVariable(required = false) Integer count) {
        return ResponseEntity.ok(this.service.generateOrders(count));
    }

    @GetMapping("/random")
    public ResponseEntity<List<OrderView>> getRandom() {
        return ResponseEntity.ok(this.service.getByRandomFilters());
    }
}
