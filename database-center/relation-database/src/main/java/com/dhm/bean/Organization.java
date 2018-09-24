package com.dhm.bean;


import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 10:07
 */
@Entity
@Table(appliesTo="organization",comment = "组织表")
public class Organization {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '组织编码'")
    private String ouCode;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '组织名称'")
    private String ouName;

    public Organization() {
    }

    public Organization(String ouCode, String ouName) {
        this.ouCode = ouCode;
        this.ouName = ouName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }
}