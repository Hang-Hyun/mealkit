package mealkit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MypageRepository extends CrudRepository<Mypage, Long> {

    List<Mypage> findByOrderId(Long orderId);
    Optional<Mypage> findByUserid(String userid);
}