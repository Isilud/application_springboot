package com.safetynet.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Firestation {
    String address;
    String station;
}
