package com.yzk.model.domain;

import java.io.Serializable;

/**
 * <pre>
 *     author : khum
 *     time   : 2018/8/30
 *     desc   :
 * </pre>
 */
public class Category implements Serializable{

    private int node_id;
    private String name;
    private int parent_id;

    @Override
    public String toString() {
        return "Category{" +
                "node_id=" + node_id +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                '}';
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
