package mealkit;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String productId;
    private String userid;
    private Integer qty;
    private String address;
    private String status;

    //@PrePersist
    @PostPersist
    public void onPostPersist(){
        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        mealkit.external.Payment payment = new mealkit.external.Payment();
        // mappings goes here

        System.out.println("*********************************************"+this.getId());
        payment.setOrderId(this.getId());  //orderId 추가
        payment.setProductId(this.getProductId());
        payment.setAddress(this.getAddress());
        payment.setQty(this.getQty());
        payment.setStatus("order confirmed!!");
        payment.setCardnumber("1111-1111");
        payment.setCardowner("hwangs card");
        OrderApplication.applicationContext.getBean(mealkit.external.PaymentService.class)
            .paymentrequest(payment);
    }

    @PostUpdate
    public void onPostUpdate() {

        if(this.getStatus().equals("Order Cancelled")) {
            Cancelled cancelled = new Cancelled();
            BeanUtils.copyProperties(this, cancelled);
            cancelled.publishAfterCommit();
        }

    }


    @PreRemove
    public void onPreRemove() {
        Cancelled cancelled = new Cancelled();
        BeanUtils.copyProperties(this, cancelled);
        cancelled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        mealkit.external.Delivery delivery = new mealkit.external.Delivery();
        // mappings goes here
        delivery.setOrderId(this.getId());  //orderId 추가
        delivery.setStatus("order canceled-delivery!!");
        OrderApplication.applicationContext.getBean(mealkit.external.DeliveryService.class)
                .deliveryCancel(delivery);


        mealkit.external.Payment payment = new mealkit.external.Payment();
        // mappings goes here
        payment.setOrderId(this.getId());  //orderId 추가
        payment.setStatus("order canceled-payment");
        OrderApplication.applicationContext.getBean(mealkit.external.PaymentService.class)
                .paymentrequest(payment);
    }

//    //고객센터 상태변경(취소)
//    @PostRemove
//    public void onPostRemove(){
//        Cancelled cancelled = new Cancelled();
//        BeanUtils.copyProperties(this, cancelled);
//        cancelled.publishAfterCommit();
//    }


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
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
