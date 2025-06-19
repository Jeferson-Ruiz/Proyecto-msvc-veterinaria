package com.jr.sav_mvsc_medicalcontrol.exeption;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class PetNotFoundException implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String key, Response response) {
        if (key.contains("PetClient#getPetById") && response.status() == 404){
            return new FeignException.NotFound(
                    "La mascota no existe en el sistema",
                    response.request(),
                        null,
                        null
            );
        }
        return defaultDecoder.decode(key, response);
    }
}