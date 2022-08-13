package com.laioffer.onlineorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity//写了entity，hibernate就知道吧这个POJO映射到数据库里
@Table(name = "restaurants")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 2455760938054036111L;

    @Id
    private int id;

    private String address;

    private String name;

    private String phone;

    private String imageUrl;
//这里的menu设计成每一道菜是一个menu了
    @OneToMany(mappedBy = "restaurant",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    在MenuItem里有个restaurant是manytoone，映射到这里的menuItemList，在one的这头需要用mappedby指定那边的many叫什么
//    mappedBy定义了拥有关联关系的字段，如果关系是单向的就不需要；如果是双向关系表，那么拥有关系的这一方有建立、解除和更新与另一方关系的能力；
//    而另一方没有这种能力，只能被动管理；这个属性被定义在关系的被拥有方。支持双向 @OneToOne，双向 @OneToMany，双向 @ManyToMany。
//cascade定义一方（One）和多方（Many）对象的级联关系，当对一方（One）对象进行了某个操作后，怎样影响到多方（Many）对象的操作。
//fetch定义关联属性何时进行加载,FetchType.EAGER：表示关系类在主类加载的时候同时加载,
// FetchType.LAZY：表示关系类在被访问时才加载，默认值是 FetchType.LAZY。
    private List<MenuItem> menuItemList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }
}