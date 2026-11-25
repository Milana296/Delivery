
package com.example.delivery.model;

public class Delivery {
    private final int id;
    private final Package parcel;
    private final Courier courier;
    private DeliveryStatus status;

    public Delivery(int id, Package parcel, Courier courier) {
        this.id = id;
        this.parcel = parcel;
        this.courier = courier;
        this.status = DeliveryStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public Package getParcel() {
        return parcel;
    }

    public Courier getCourier() {
        return courier;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
