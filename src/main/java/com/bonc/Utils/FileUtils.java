package com.bonc.Utils;

import java.io.File;
import java.util.*;

/**
 * Created by ZK on 2016/11/28.
 */
public class FileUtils {

    //递归查找指定目录下的所有指定扩展名文件，将返回文件名的list
    public static List<String> findByEx(String suffix,String dirName) {
        File file = new File(dirName);
        File[] files = file.listFiles();
        List result = new ArrayList();
        for (File f : files) {
            if (f.isDirectory()) {
                result.addAll(findByEx(suffix,f.getPath()));
            } else if (f.getName().endsWith("." + suffix)) {
                result.add(f.getPath());
            }
        }
        return result;
    }

    //返回无扩展名的文件名称
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static Map mapClone(Map map){
        Map newMap = new HashMap();
        Set<Map.Entry> entrySet = map.entrySet();
        Iterator<Map.Entry> i = entrySet.iterator();
        while (i.hasNext()) {
            Map.Entry me = i.next();
            String key = me.getKey().toString();
            String value = me.getValue().toString();
            newMap.put(key,value);
        }
        return newMap;
    }
}
