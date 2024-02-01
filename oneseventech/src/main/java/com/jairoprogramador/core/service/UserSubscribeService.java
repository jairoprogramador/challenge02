package com.jairoprogramador.core.service;

import com.jairoprogramador.core.model.aggregate.SubcriptionAgregate;

public interface UserSubscribeService {
    SubcriptionAgregate subscribe(SubcriptionAgregate subcriptionAgregate);
}
