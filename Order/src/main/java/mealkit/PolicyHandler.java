package mealkit;

import mealkit.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired//추가
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCompleted_UpdateStatus(@Payload DeliveryCompleted deliveryCompleted){

        if(deliveryCompleted.isMe()){
            Optional<Order> orderOptional = orderRepository.findById(deliveryCompleted.getOrderid());
            Order order = orderOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
            order.setStatus("delivery completed");
            orderRepository.save(order);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryRequest_UpdateStatus(@Payload DeliveryRequested deliveryRequest){

        if(deliveryRequest.isMe()){

            System.out.println("**********************************");
            System.out.println("*******"+deliveryRequest.getOrderid()+"*********");
            System.out.println("**********************************");
            Optional<Order> orderOptional = orderRepository.findById(deliveryRequest.getOrderid());
            Order order = orderOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
            order.setStatus("delivery Requested");
            orderRepository.save(order);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCancel_UpdateStatus(@Payload DeliveryCancelled deliveryCancelled){

        if(deliveryCancelled.isMe()){
            Optional<Order> orderOptional = orderRepository.findById(deliveryCancelled.getOrderId());
            Order order = orderOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
            order.setStatus("delivery Requested");
            orderRepository.save(order);
        }
    }


}
