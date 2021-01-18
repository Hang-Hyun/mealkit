package mealkit;

public class DeliveryCompleted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String status;
    private String rcvPhoneNumber;
    private String message;
    private String sndPhoneNumber;
    private String email;

    public DeliveryCompleted(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRcvPhoneNumber() {
        return rcvPhoneNumber;
    }

    public void setRcvPhoneNumber(String rcvPhoneNumber) {
        this.rcvPhoneNumber = rcvPhoneNumber;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getSndPhoneNumber() {
        return sndPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
