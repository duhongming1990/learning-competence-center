package com.dhm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/5 09:46
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JacksonDemoBean {

    @JsonProperty("deptName")
    private String dept;

    private String name;

    @JsonIgnore
    private String password;

    private Integer age;
}
