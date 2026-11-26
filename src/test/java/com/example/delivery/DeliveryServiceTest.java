
package com.example.delivery;

import com.example.delivery.model.Customer;
import com.example.delivery.model.Courier;
import com.example.delivery.model.Package;
import com.example.delivery.model.Delivery;
import com.example.delivery.model.DeliveryStatus;

import com.example.delivery.repository.DeliveryRepository;
import com.example.delivery.repository.InMemoryDeliveryRepository;
import com.example.delivery.service.DeliveryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryServiceTest {

    private DeliveryService service;

    // Подготовка перед каждым тестом
    @BeforeEach
    void setUp() {

        DeliveryRepository repository =
                new InMemoryDeliveryRepository();

        service = new DeliveryService(repository);

        Customer customer =
                new Customer(1, "Милана", "Гомель");

        Courier courier =
                new Courier(1, "Ульяна");

        Package parcel =
                new Package(1, "Книга", customer);

        Delivery delivery =
                new Delivery(1, parcel, courier);

        service.createDelivery(delivery);
    }


    @Test
    void shouldCompleteDeliverySuccessfully() {

        service.changeStatus(1, DeliveryStatus.ACCEPTED);
        service.changeStatus(1, DeliveryStatus.IN_TRANSIT);
        service.changeStatus(1, DeliveryStatus.DELIVERED);

        Delivery delivery =
                service.findDelivery(1).orElseThrow();

        assertEquals(
                DeliveryStatus.DELIVERED,
                delivery.getStatus()
        );
    }

// cp "/Users/milana_sch/IdeaProjects/Delivery/src/test/java/com/example/delivery/DeliveryServiceTest.java" ~/.jenkins/workspace/Delivery-Pipeline/src/test/java/com/example/delivery/DeliveryServiceTest.java
    @Test
    void shouldRejectSkippingStatus() {

        assertThrows(
                IllegalStateException.class,
                () -> service.changeStatus(
                        1,
                        DeliveryStatus.DELIVERED
                )
        );
    }


    @Test
    void shouldRejectReturningToPreviousStatus() {

        service.changeStatus(1, DeliveryStatus.ACCEPTED);
        service.changeStatus(1, DeliveryStatus.IN_TRANSIT);
        service.changeStatus(1, DeliveryStatus.DELIVERED);

        assertThrows(
                IllegalStateException.class,
                () -> service.changeStatus(
                        1,
                        DeliveryStatus.CREATED
                )
        );
    }


    @Test
    void shouldRejectEmptyPackageDescription() {

        Customer customer =
                new Customer(2, "Анна", "Минск");

        Courier courier =
                new Courier(2, "Иван");

        Package parcel =
                new Package(2, "   ", customer);

        Delivery delivery =
                new Delivery(2, parcel, courier);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createDelivery(delivery)
        );
    }
    @Test
    void shouldCancelDeliverySuccessfully() {

        service.cancelDelivery(1);

        Delivery delivery = service.findDelivery(1).orElseThrow();

        assertEquals(
                DeliveryStatus.CANCELLED,
                delivery.getStatus()
        );
    }

    @Test
    void shouldRejectCancellationAfterShipment() {

        service.changeStatus(1, DeliveryStatus.ACCEPTED);
        service.changeStatus(1, DeliveryStatus.IN_TRANSIT);

        assertThrows(
                IllegalStateException.class,
                () -> service.cancelDelivery(1)
        );
    }
}
