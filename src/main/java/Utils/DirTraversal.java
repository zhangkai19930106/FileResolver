package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZK on 2016/11/28.
 */
public class DirTraversal {

    //递归查找指定目录下的所有指定拓展名的文件，将返回文件名的list
    public static List<String> findXml(String suffix,String dirName) {
        File file = new File(dirName);
        File[] files = file.listFiles();
        List result = new ArrayList();
        for (File f : files) {
            if (f.isDirectory()) {
                result.addAll(findXml(suffix,f.getPath()));
            } else if (f.getName().endsWith("." + suffix)) {
                result.add(f.getPath());
            }
        }
        return result;
    }
}
