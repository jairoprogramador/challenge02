package com.jairoprogramador.controller;

import com.jairoprogramador.controller.input.PaymentInput;
import com.jairoprogramador.entities.PaymentEntity;
import com.jairoprogramador.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<PaymentEntity> createPayment(@RequestBody PaymentInput paymentInput) {
        try {
            var url = this.pagar();
            System.out.println("huksdlf " + url);
            return ResponseEntity.status(HttpStatus.OK).location(URI.create(url)).build();
        //return ResponseEntity.ok(paymentService.save(paymentInput));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentEntity>> get(@PathVariable(required = true) int id) {
        try {
            return ResponseEntity.ok(paymentService.findByUserId(BigInteger.valueOf(id)));
        }catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).build();
        }
    }

    public String  pagar(){
        String YOUR_DOMAIN = "http://localhost:4200";
        try {
            ProductCreateParams paramsProduct = ProductCreateParams.builder().setName("Anual").build();
            Product product = Product.create(paramsProduct);

            PriceCreateParams paramsPrice =
                    PriceCreateParams.builder()
                            .setProduct(product.getId())
                            .setUnitAmount(90L)
                            .setCurrency("usd")
                            .build();

            Price price = Price.create(paramsPrice);


            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(YOUR_DOMAIN + "/dashboard")
                            .setCancelUrl(YOUR_DOMAIN + "/dashboard")
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                            .setPrice(price.getId())
                                            .build())
                            .build();
            Session session = Session.create(params);
            return  session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
