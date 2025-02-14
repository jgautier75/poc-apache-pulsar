package com.acme.jga.pulsar.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppTenant {
    private String name;
    private String cluster;
}
