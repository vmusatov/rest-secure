package com.restsecure.core.http.header;

import com.restsecure.core.http.NameAndValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Header implements NameAndValue {
    private String name;
    private String value;
}
