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
    ProductRepository productRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_Modify(@Payload PaymentCompleted paymentCompleted){

        if(paymentCompleted.isMe()){
            Optional<Product> productOptional = productRepository.findById(Long.valueOf(paymentCompleted.getProductId()).longValue());
            Product product = productOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
            System.out.println("##### product.getQty() : " + product.getQty());
            System.out.println("##### paymentCompleted.getQty() : " + paymentCompleted.getQty());
            Integer modiQty = product.getQty()-paymentCompleted.getQty();
            product.setQty(modiQty);
            productRepository.save(product);
        }
    }

//    @StreamListener(KafkaProcessor.INPUT)
//    public void wheneverShipped_UpdateStatus(@Payload Shipped shipped){
//
//        if(shipped.isMe()){
//            //추가
//            Optional<Order> orderOptional = orderRepository.findById(shipped.getId());
//            Order order = orderOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
//            order.setStatus((shipped.getStatus()));
//            orderRepository.save(order);
//            System.out.println("##### listener UpdateStatus : " + shipped.toJson());
//        }
//    }





    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_Modify(@Payload PaymentCancelled paymentCancelled){

        if(paymentCancelled.isMe()){
            System.out.println("##### listener Modify : " + paymentCancelled.toJson());
            Optional<Product> productOptional = productRepository.findById(Long.valueOf(paymentCancelled.getProductId()).longValue());
            Product product = productOptional.get();//위에서 find한 오더 객체를 찾아서 매핑
            System.out.println("##### product.getQty() : " + product.getQty());
            System.out.println("##### paymentCompleted.getQty() : " + paymentCancelled.getQty());
            Integer modiQty = product.getQty()+paymentCancelled.getQty();
            product.setQty(modiQty);
            productRepository.save(product);
        }
    }

}
