package com.bonc.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZK on 2016/12/8.
 */
public class Node {

    private String name;
    private String content;
    private List<Node> children;

    public Node(String name){
        this.name = name;
        children = new ArrayList<Node>();
    }

    @Override
    public String toString(){
        if (children.size()>0){
            return "{\"" + name + "\":" + children.toString() + "}";
        }else {
            return "{\"" + name + "\":" + "\"" + content + "\"}";
        }
    }

    public void addContent(String content){
        this.content = content;
    }
    public void addChild(Node child){
        this.children.add(child);
    }
    public List getChildren(){
        return this.children;
    }

}
