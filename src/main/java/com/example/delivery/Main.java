
package com.example.delivery;

import com.example.delivery.model.*;
import com.example.delivery.model.Package;
import com.example.delivery.repository.DeliveryRepository;
import com.example.delivery.repository.InMemoryDeliveryRepository;
import com.example.delivery.service.DeliveryService;

public class Main {
    public static void main(String[] args) {
        DeliveryRepository repository =
                new InMemoryDeliveryRepository();

        DeliveryService service = new DeliveryService(repository);

        Customer customer = new Customer(
                1, "Милана", "Гомель, ул. Мазурова, 10"
        );

        Courier courier = new Courier(1, "Ульяна");

        Package parcel = new Package(
                1, "Посылка с книгами", customer
        );

        Delivery delivery = new Delivery(1, parcel, courier);

        service.createDelivery(delivery);

        System.out.println("Доставка создана: "
                + service.findDelivery(1).get().getStatus());

        service.changeStatus(1, DeliveryStatus.ACCEPTED);
        System.out.println("Заказ принят: "
                + service.findDelivery(1).get().getStatus());

        service.changeStatus(1, DeliveryStatus.IN_TRANSIT);
        System.out.println("Посылка в пути: "
                + service.findDelivery(1).get().getStatus());

        service.changeStatus(1, DeliveryStatus.DELIVERED);
        System.out.println("Итоговый статус: "
                + service.findDelivery(1).get().getStatus());

        // Проверяем запрет недопустимого перехода
        try {
            service.changeStatus(1, DeliveryStatus.CREATED);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка обработана: " + e.getMessage());
        }
    }
}
