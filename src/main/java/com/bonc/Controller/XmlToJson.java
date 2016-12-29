package com.bonc.Controller;

import com.bonc.Entity.Node;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZK on 2016/12/7.
 */
        @RestController
        @EnableAutoConfiguration
        @RequestMapping("/xmltojson")
        public class XmlToJson {


            @RequestMapping("/aaa")
            public void main() throws Exception{
                XmlToJson xmlToJson = new XmlToJson();
                System.out.println(xmlToJson.operation(new File("C:\\Users\\ZK\\Desktop\\test.xml")));
            }

            public String operation(File file) throws Exception {
                BufferedReader br = new BufferedReader(new FileReader(file));
                Pattern tag_head = Pattern.compile("<([^>|^/|^?]+)>");
                Pattern content_pattern = Pattern.compile(">([^<]*)<");
                Pattern tag_tail = Pattern.compile("</([^>]+)>");
                String b;
                Stack stack = new Stack();
                Node root = new Node("root");
                Node currNode = root;
                TreeMap<String, Node> nodeMap = new TreeMap<String, Node>();
                String lastKey = "root";
                while ((b = br.readLine()) != null) {
                    Matcher head_matcher = tag_head.matcher(b);
                    Matcher content_matcher = content_pattern.matcher(b);
                    Matcher tail_matcher = tag_tail.matcher(b);
                    if (head_matcher.find() && tail_matcher.find() == false) {
                        nodeMap.put(lastKey, currNode);
                        stack.push(lastKey);
                        currNode = new Node(head_matcher.group(1));
                        lastKey = head_matcher.group(1);
                        continue;
                    }
                    if (content_matcher.find()) {
                        Node node = new Node(head_matcher.group(1));
                        node.addContent(content_matcher.group(1));
                        currNode.addChild(node);
                        continue;
                    }
                    if (head_matcher.find() == false && tail_matcher.find()) {
                        String key = stack.pop().toString();
                        Node lastNode = nodeMap.get(key);
                        lastNode.addChild(currNode);
                        currNode = lastNode;
                        continue;
                    }
                }
                return root.getChildren().toString();
            }

    }

