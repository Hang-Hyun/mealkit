package mealkit;

public class DeliveryCancelled extends AbstractEvent {

    private Long id;

    public DeliveryCancelled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
