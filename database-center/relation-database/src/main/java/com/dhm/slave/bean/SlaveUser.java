package com.dhm.slave.bean;

import javax.persistence.*;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 14:21
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "slave_user", comment = "从库用户表")
public class SlaveUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,columnDefinition="varchar(100) COMMENT '用户名'")
    private String userName;

    @Column(nullable = false,columnDefinition="int(3) COMMENT '年龄'")
    private Integer age;

    public SlaveUser() {
    }

    public SlaveUser(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}