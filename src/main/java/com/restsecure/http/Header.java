package com.restsecure.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Header implements NameAndValue {
    private String name;
    private String value;
}
