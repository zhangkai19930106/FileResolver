package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZK on 2016/11/28.
 */
public class FileUtils {

    //�ݹ����ָ��Ŀ¼�µ�����ָ����չ�����ļ����������ļ�����list
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

    //��������չ�����ļ�����
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
