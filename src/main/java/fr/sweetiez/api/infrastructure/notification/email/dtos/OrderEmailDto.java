package fr.sweetiez.api.infrastructure.notification.email.dtos;

import javax.validation.constraints.Email;
import java.util.List;

public record OrderEmailDto(
        @Email
        String to,
        String subject,
        String firstName,
        String lastName,
        String commandId,
        String pickupDate,
        double total,
        List<ProductEmailDto> products,
        String rewardName){
}
