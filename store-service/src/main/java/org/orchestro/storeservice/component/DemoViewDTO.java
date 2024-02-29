package org.orchestro.storeservice.component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoViewDTO {

    private String name;
    private String age;
    private String id;
    private String department;
}
