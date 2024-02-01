package com.jairoprogramador.presentation.controllers.factory;

import com.jairoprogramador.core.model.aggregate.SubcriptionAgregate;
import com.jairoprogramador.presentation.controllers.input.UserSubscriptionInput;
import com.jairoprogramador.presentation.controllers.output.UserSubscriptionOutput;

public interface UserSubscriptionIO {
    SubcriptionAgregate createEntityFromInput(UserSubscriptionInput input);
    UserSubscriptionOutput createOutputFromEntity(SubcriptionAgregate user);
}
