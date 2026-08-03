package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.ShipmentStatus;

public class UpdateShipmentStatusDTO {

    private ShipmentStatus shipmentStatus;

    public UpdateShipmentStatusDTO() {
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }
}