package mealkit;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String productId;
    private Integer qty;
    private String cardnumber;
    private String cardowner;
    private String approvednumber;
    private String status;
    private String address;

    @PostPersist
    public void onPostPersist(){
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+this.getStatus());
        if(!this.getStatus().equals("order canceled-payment")){
            PaymentCompleted paymentCompleted = new PaymentCompleted();

            BeanUtils.copyProperties(this, paymentCompleted);
            System.out.println("payment$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+paymentCompleted.getStatus());
            paymentCompleted.publishAfterCommit();
        }else{
            System.out.println("$$$$$$$$$$$$$$$$$$$ PaymentCancelled $$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+this.getOrderId());


            PaymentCancelled paymentCancelled = new PaymentCancelled();
            BeanUtils.copyProperties(this, paymentCancelled);
            System.out.println("payment$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+paymentCancelled.getStatus());
            paymentCancelled.publishAfterCommit();
        }

    }

    @PostUpdate
    public void onPostPersistPaymentCanceled(){
        System.out.println("$$$$$$$$$$$$$$$$$$$ Payment PostUpdate $$$$$$$$$$$$$$$$$$$$$$$$$");
        /*
        PaymentCompleted paymentCompleted = new PaymentCompleted();
        BeanUtils.copyProperties(this, paymentCompleted);
        paymentCompleted.publishAfterCommit();
        */

        PaymentCancelled paymentCancelled = new PaymentCancelled();
        BeanUtils.copyProperties(this, paymentCancelled);
        paymentCancelled.publishAfterCommit();
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }
    public String getCardowner() {
        return cardowner;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setCardowner(String cardowner) {
        this.cardowner = cardowner;
    }
    public String getApprovednumber() {
        return approvednumber;
    }

    public void setApprovednumber(String approvednumber) {
        this.approvednumber = approvednumber;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}
