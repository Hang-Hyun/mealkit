package mealkit;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Product_table")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String prodNm;
    private String sellerId;
    private Integer qty;

    @PrePersist
    public void onPrePersist(){
        ProductRegistered productRegistered = new ProductRegistered();
        BeanUtils.copyProperties(this, productRegistered);
        productRegistered.publishAfterCommit();


        ProductModified productModified = new ProductModified();
        BeanUtils.copyProperties(this, productModified);
        productModified.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }




}
