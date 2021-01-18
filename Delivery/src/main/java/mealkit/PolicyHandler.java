package mealkit;

import mealkit.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    //추가
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_Ship(@Payload PaymentCompleted paymentCompleted){

        if(paymentCompleted.isMe()){
            Delivery delivery = new Delivery();
            //delivery.setId(paymentCompleted.getOrderId());
            delivery.setOrderId(paymentCompleted.getOrderId());
            delivery.setAddress(paymentCompleted.getAddress());
            delivery.setStatus("delivery started");
            deliveryRepository.save(delivery);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_CancelShip(@Payload PaymentCancelled paymentCancelled){

        if(paymentCancelled.isMe()){
            List<Delivery> deliveryOptional = deliveryRepository.findByOrderId(Long.valueOf(paymentCancelled.getOrderId()));

            for(Delivery delivery:deliveryOptional){
                delivery.setStatus("delivery Cancelled");
                deliveryRepository.save(delivery);
            }
        }
    }

}
