package com.demo.project.entity;

/**
 *
   This class was automatically generated by the data modeler tool.
 * @author xuebaopeng
 * Description
 */
public class DroolsMytmodel implements java.io.Serializable{
    static final long serialVersionUID = 1L;

    private java.lang.Integer id;
    private java.lang.String name;
    private java.lang.Integer sex;

    public DroolsMytmodel() {
    }

    public java.lang.Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.Integer getSex() {
        return this.sex;
    }

    public void setSex(java.lang.Integer sex) {
        this.sex = sex;
    }

    public DroolsMytmodel(Integer id, String name, Integer sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "mytestmodel1 [id=" + id + ", name=" + name + ", sex=" + sex + "]";
    }

}
