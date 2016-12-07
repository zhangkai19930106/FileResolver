package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZK on 2016/11/28.
 */
public class FileUtils {

    public static void createFolder(String originalPathAndFileName){
        String tempFileName=originalPathAndFileName.substring(0,originalPathAndFileName.lastIndexOf("\\"));   //获取文件名前的部分，以便创建存放txt文件的文件夹
        File tempFile = new File(tempFileName+"\\dealt");                   //创建文件夹
        if(!tempFile.exists() && !tempFile.isDirectory()){
            tempFile.mkdir();                               //如果不存在此名称的文件夹，就创建一个
        }
    }

    //递归查找指定目录下的所有指定拓展名的文件，将返回文件名的list
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
}
