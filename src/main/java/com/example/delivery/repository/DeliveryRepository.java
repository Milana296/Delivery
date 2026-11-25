
package com.example.delivery.repository;

import com.example.delivery.model.Delivery;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {
    void save(Delivery delivery);

    Optional<Delivery> findById(int id);

    List<Delivery> findAll();
}
