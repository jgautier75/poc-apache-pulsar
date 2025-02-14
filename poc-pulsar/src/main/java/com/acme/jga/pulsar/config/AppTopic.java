package com.acme.jga.pulsar.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppTopic {
    private String name;
    private int nbOfPartitions;
    private String namespace;
}
