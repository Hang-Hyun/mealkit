package mealkit;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Notification_table")
public class Notification {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String rcvPhoneNumber;
    private String message;
    private String sndPhoneNumber;
    private String status;
    private String email;
    private Long orderId;

    @PostUpdate
    public void onPrePersist(){
        Notified notified = new Notified();
        BeanUtils.copyProperties(this, notified);
        notified.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setSndPhoneNumber(String sndPhoneNumber) {
        this.sndPhoneNumber = sndPhoneNumber;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }




}
