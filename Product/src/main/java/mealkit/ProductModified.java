package mealkit;

public class ProductModified extends AbstractEvent {

    private Long id;
    private Integer qty;

    public ProductModified(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
