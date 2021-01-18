package mealkit;

public class DeliveryCompleted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String status;
    private String userid;

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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
