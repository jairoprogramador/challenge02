package com.jairoprogramador.services;

import com.jairoprogramador.entities.SubscriptionEntity;
import com.jairoprogramador.entities.UserEntity;
import com.jairoprogramador.repositories.SubscriptionRepository;
import com.jairoprogramador.repositories.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Transactional
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public  Iterable<SubscriptionEntity> findAll(){
        return subscriptionRepository.findAll();
    }


    public String getURLPayment(BigInteger subscriptionId){
        return createURLPayment(this.getSubscription(subscriptionId), this.getUser());
    }

    private String createURLPayment(SubscriptionEntity subscription, UserEntity user){
        var paramsSuccess = "?uId="+user.getId()+"&sId="+subscription.getId();
        var paramsError = "?uId=-1&sId=-1";

        String YOUR_DOMAIN = "http://localhost:4200";
        try {
            ProductCreateParams paramsProduct = ProductCreateParams.builder().setName(subscription.getType()).build();
            Product product = Product.create(paramsProduct);

            PriceCreateParams paramsPrice =
                    PriceCreateParams.builder()
                            .setProduct(product.getId())
                            .setUnitAmount(subscription.getPrice().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValue())
                            .setCurrency("usd")
                            .build();

            Price price = Price.create(paramsPrice);


            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(YOUR_DOMAIN + "/dashboard" + paramsSuccess)
                            .setCancelUrl(YOUR_DOMAIN + "/dashboard" + paramsError)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPrice(price.getId())
                                            .build())
                            .build();
            Session session = Session.create(params);
            return  session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private  UserEntity getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private  SubscriptionEntity getSubscription(BigInteger subscriptionId){
        return this.subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

}