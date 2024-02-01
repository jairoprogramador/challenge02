package com.jairoprogramador.presentation.controllers.common;

import com.jairoprogramador.core.model.aggregate.SubcriptionAgregate;
import com.jairoprogramador.core.model.entity.UserEntity;
import com.jairoprogramador.core.model.valueobject.TypeSubscriptionValueObject;
import com.jairoprogramador.presentation.controllers.factory.UserSubscriptionIO;
import com.jairoprogramador.presentation.controllers.input.UserSubscriptionInput;
import com.jairoprogramador.presentation.controllers.output.UserSubscriptionOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CommonUserSubcriptionIO implements UserSubscriptionIO {

    @Override
    public SubcriptionAgregate createEntityFromInput(UserSubscriptionInput input) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(input.getUserId());

        TypeSubscriptionValueObject type = new TypeSubscriptionValueObject(input.getSubscriptionId());

        return  new SubcriptionAgregate(userEntity, type);
    }

    @Override
    public UserSubscriptionOutput createOutputFromEntity(SubcriptionAgregate agregate) {
        UserSubscriptionOutput userOutput = new UserSubscriptionOutput();
        userOutput.setId(agregate.getId());
        userOutput.setSubscriptionId(agregate.getType().getId());
        userOutput.setUserId(agregate.getUser().getId());
        return  userOutput;
    }
}
