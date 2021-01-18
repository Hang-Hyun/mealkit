package mealkit;

public class DeliveryRequested extends AbstractEvent {


    private Long id;
    private Long orderid;

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
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
}
