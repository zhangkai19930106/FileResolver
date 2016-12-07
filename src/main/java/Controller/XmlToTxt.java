package Controller;

import Utils.FileUtils;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KayZq on 2016/12/6.
 */
public class XmlToTxt {
    private String originalPathAndFileName;     //原始未处理过路径+文件名。例如：C:\Users\KayZq\Desktop\2016112051_SHEETS_3.xml
    private String dealPath;             //处理后的路径，可以用户指定，否则在原来的路径下生成文件
    private String separate="|";        //分隔符号

    public XmlToTxt(String originalPathAndFileName,String dealPath){
        this.originalPathAndFileName = originalPathAndFileName;
        this.dealPath = dealPath;
        this.dealPath=FileUtils.getFileNameNoEx(originalPathAndFileName);                   //将文件名传入并进行处理
    }
    public XmlToTxt(String originalPathAndFileName){
        this.originalPathAndFileName = originalPathAndFileName;
        String fileName = originalPathAndFileName.substring(originalPathAndFileName.lastIndexOf("\\"),originalPathAndFileName.lastIndexOf("."));
        this.dealPath = originalPathAndFileName.substring(0,originalPathAndFileName.lastIndexOf("\\"))+"\\dealt"+fileName+".txt";//用户没有输入处理后的路径,则在原始路径下生成一个dealt文件夹保存
        FileUtils.createFolder(originalPathAndFileName);          //创建文件夹
    }
    public void operation()throws Exception{
        long StartTime = System.currentTimeMillis();                //获取操作开始时间
        BufferedReader bufferedReader = new BufferedReader(new FileReader(originalPathAndFileName));  //定义一个带缓冲的字节输入流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dealPath));  //定义一个带缓冲的字节输出流

        String b;
        Pattern allElement = Pattern.compile(">([^</]+)</");//所有元素内容的正则表达式
        Pattern superElement = Pattern.compile("</([^>]+)>");//匹配完一个同级元素
        while((b=bufferedReader.readLine())!=null){                    //如果没有复制完就循环
            Matcher context = allElement.matcher(b);
            Matcher superContext = superElement.matcher(b);
            if(context.find()){
                bufferedWriter.write(context.group(1));         //将提取的内容写入文件
                bufferedWriter.write(separate);                 //将用户输入的分隔符写入文件
            }
            if(!superContext.find()){
                bufferedWriter.newLine();           //换行
            }
        }
        bufferedReader.close();                 //关闭流
        bufferedWriter.close();
        long EndTime = System.currentTimeMillis();                    //获取操作结束时间
        System.out.println("NoDeal: "+originalPathAndFileName);
        System.out.println("Dealt: "+dealPath + "   Time:" + (EndTime - StartTime) + " ms");
    }
    public static void main(String[] args){
        List<String> filePathAndFileName = FileUtils.findByEx("xml", "C:\\Users\\KayZq\\Desktop\\dealt");
        for(String f:filePathAndFileName){
            XmlToTxt xmlToTxt = new XmlToTxt(f);
            try {
                xmlToTxt.operation();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("文件不存在");
            }
        }
    }
}
