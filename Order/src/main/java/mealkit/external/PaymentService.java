
package mealkit.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

//@FeignClient(name="payment", url="http://Payment:8080")
@FeignClient(name="delivery", url="${api.payment.url}")
public interface PaymentService {

    @RequestMapping(method= RequestMethod.GET, path="/payments")
    public void paymentrequest(@RequestBody Payment payment);


}