
package com.example.delivery.service;

import com.example.delivery.model.Delivery;
import com.example.delivery.model.DeliveryStatus;
import com.example.delivery.repository.DeliveryRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }


    public void createDelivery(Delivery delivery) {


        if (StringUtils.isBlank(delivery.getParcel().getDescription())) {
            throw new IllegalArgumentException(
                    "Описание посылки не может быть пустым"
            );
        }


        if (repository.findById(delivery.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "Доставка с таким ID уже существует"
            );
        }

        repository.save(delivery);
    }


    public void changeStatus(int deliveryId, DeliveryStatus newStatus) {

        Delivery delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Доставка не найдена: " + deliveryId
                ));

        DeliveryStatus currentStatus = delivery.getStatus();

        if (!isTransitionAllowed(currentStatus, newStatus)) {
            throw new IllegalStateException(
                    "Недопустимый переход: " + currentStatus
                            + " -> " + newStatus
            );
        }

        delivery.setStatus(newStatus);
        repository.save(delivery);
    }


    private boolean isTransitionAllowed(
            DeliveryStatus current,
            DeliveryStatus next
    ) {
        if (current == DeliveryStatus.CREATED) {
            return next == DeliveryStatus.ACCEPTED
                    || next == DeliveryStatus.CANCELLED;
        }

        if (current == DeliveryStatus.ACCEPTED) {
            return next == DeliveryStatus.IN_TRANSIT
                    || next == DeliveryStatus.CANCELLED;
        }

        if (current == DeliveryStatus.IN_TRANSIT) {
            return next == DeliveryStatus.DELIVERED;
        }

        return false;
    }


    public Optional<Delivery> findDelivery(int id) {
        return repository.findById(id);
    }


    public List<Delivery> findAllDeliveries() {
        return repository.findAll();
    }
}
