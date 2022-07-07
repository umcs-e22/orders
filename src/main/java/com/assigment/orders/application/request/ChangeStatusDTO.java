package com.assigment.orders.application.request;

import com.assigment.orders.domain.models.OrderStatus;
import lombok.Data;

@Data
public class ChangeStatusDTO {
    OrderStatus status;
}
