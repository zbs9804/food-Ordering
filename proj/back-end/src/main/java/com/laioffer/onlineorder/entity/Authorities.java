package com.laioffer.onlineorder.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity//Entities in JPA are nothing but POJOs。凡是属于entity的数据都要加这一句，让spring知道
@Table(name = "authorities")//指定这个column在数据库里的名字，如果不指定，就用class的类名
    public class Authorities implements Serializable {
    //serializable：硬盘上都是01码，serializable告诉JVM将java object转换成stream存储到硬盘

    private static final long serialVersionUID = 8734140534986494039L;//将class存到数据库里的版本号

    @Id//指定主键
    private String email;

    private String authorities;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}