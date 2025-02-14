package com.acme.jga.pulsar.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppNamespace {
    private String name;
    private int nbOfBundles;
}
