
package com.example.delivery.repository;

import com.example.delivery.model.Delivery;

import java.util.*;

public class InMemoryDeliveryRepository implements DeliveryRepository {
    private final Map<Integer, Delivery> deliveries = new HashMap<>();

    @Override
    public void save(Delivery delivery) {
        deliveries.put(delivery.getId(), delivery);
    }

    @Override
    public Optional<Delivery> findById(int id) {
        return Optional.ofNullable(deliveries.get(id));
    }

    @Override
    public List<Delivery> findAll() {
        return new ArrayList<>(deliveries.values());
    }
}
