package org.koreait.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

 // getter, setter ,equalsAndHashcode,toString
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class BoardViewId {
    private Long seq;
    private Integer uid;
}
