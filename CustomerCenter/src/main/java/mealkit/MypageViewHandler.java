package mealkit;

import mealkit.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload PaymentCompleted paymentCompleted) {
        try {
            if (paymentCompleted.isMe()) {
                // view 객체 생성
                Mypage mypage = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함
                mypage.setOrderId(paymentCompleted.getOrderId());
                mypage.setProductId(paymentCompleted.getProductId());
                mypage.setQty(paymentCompleted.getQty());
                mypage.setStatus(paymentCompleted.getStatus());
                mypage.setCardnumber(paymentCompleted.getCardnumber());
                mypage.setCardowner(paymentCompleted.getCardowner());
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCompleted_then_UPDATE_1(@Payload PaymentCompleted paymentCompleted) {
        try {
            if (paymentCompleted.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(paymentCompleted.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setCardnumber(paymentCompleted.getCardnumber());
                    mypage.setCardowner(paymentCompleted.getCardowner());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCancelled_then_UPDATE_2(@Payload PaymentCancelled paymentCancelled) {
        try {
            if (paymentCancelled.isMe()) {
                // view 객체 조회
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-orderId-"+paymentCancelled.getOrderId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-id-"+paymentCancelled.getId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-status-"+paymentCancelled.getStatus());
                System.out.println("@@@@@@@@@@@!!!@@@@@@@@@@@@");
                List<Mypage> mypageList = mypageRepository.findByOrderId(paymentCancelled.getOrderId());
                for(Mypage mypage : mypageList){
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@--...");
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(paymentCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeiliverRequested_then_UPDATE_2(@Payload DeliveryRequested deliveryRequested) {
        try {
            if (deliveryRequested.isMe()) {
                // view 객체 조회
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-orderId-"+deliveryRequested.getOrderid());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-id-"+deliveryRequested.getId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-status-"+deliveryRequested.getStatus());
                System.out.println("@@@@@@@@@@@!!!@@@@@@@@@@@@");
                List<Mypage> mypageList = mypageRepository.findByOrderId(deliveryRequested.getOrderid());
                for(Mypage mypage : mypageList){
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@--...");
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(deliveryRequested.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCompleted_then_UPDATE_2(@Payload DeliveryCompleted deliveryCompleted) {
        try {
            if (deliveryCompleted.isMe()) {
                // view 객체 조회
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-orderId-"+deliveryCompleted.getOrderid());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-id-"+deliveryCompleted.getId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-status-"+deliveryCompleted.getStatus());
                System.out.println("@@@@@@@@@@@!!!@@@@@@@@@@@@");
                List<Mypage> mypageList = mypageRepository.findByOrderId(deliveryCompleted.getOrderid());
                for(Mypage mypage : mypageList){
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@--...");
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(deliveryCompleted.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCancelled_then_UPDATE_2(@Payload DeliveryCancelled deliveryCancelled) {
        try {
            if (deliveryCancelled.isMe()) {
                // view 객체 조회
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-orderId-"+deliveryCancelled.getOrderid());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-id-"+deliveryCancelled.getId());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@-status-"+deliveryCancelled.getStatus());
                System.out.println("@@@@@@@@@@@!!!@@@@@@@@@@@@");
                List<Mypage> mypageList = mypageRepository.findByOrderId(deliveryCancelled.getOrderid());
                for(Mypage mypage : mypageList){
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@--...");
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setStatus(deliveryCancelled.getStatus());
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}