package org.example.booklibrary.core.inventory.web;

public class BookInventoryRequest {
    private InventoryAction action;
    private Integer quantity;

    public InventoryAction getAction() {
        return action;
    }

    public void setAction(InventoryAction action) {
        this.action = action;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
