package mealkit;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>{
    Optional<Payment> findByOrderId(long orderId);
}