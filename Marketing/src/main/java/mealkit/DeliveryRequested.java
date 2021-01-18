package mealkit;

public class DeliveryRequested extends AbstractEvent {

    private Long id;

    public DeliveryRequested(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
