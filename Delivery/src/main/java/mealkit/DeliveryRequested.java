package mealkit;

public class DeliveryRequested extends AbstractEvent {

    private Long id;
    private Long orderId;

    public DeliveryRequested(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderId;
    }

    public void setOrderid(Long orderId) {
        this.orderId = orderId;
    }
}
