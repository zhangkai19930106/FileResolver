package Controller;

import Utils.FileUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KayZq on 2016/12/6.
 */
public class XmlToTxt {
    private String originalPath;             //原始路径
    private String dealPath;             //处理后的路径
    private String fileName;
    private String separate="|";        //分隔符号

    public void XmlToTxt(String fileName,String path)throws Exception{

    }
    public void XmlToTxt(String originalPathAndfileName)throws Exception{
        long StartTime = System.currentTimeMillis();                //获取操作开始时间
        String tempFileName=fileName.substring(0,fileName.lastIndexOf("\\"));   //获取文件名前的部分，以便创建存放txt文件的文件夹
        File tempFile = new File(tempFileName+"\\dealt");                   //创建文件夹
        if(!tempFile.exists() && !tempFile.isDirectory()){
            tempFile.mkdir();                               //如果不存在此名称的文件夹，就创建一个
        }
        FileUtils.getFileNameNoEx(fileName);                   //将文件名传入并进行处理

        BufferedReader bufferedReader = new BufferedReader(new FileReader(originalUri));  //定义一个带缓冲的字节输入流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dealUri));  //定义一个带缓冲的字节输出流

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
        System.out.println("解析前文件位置："+originalUri);
        System.out.println("解析后文件位置："+dealUri + "   耗时:" + (EndTime - StartTime) + " ms");
    }
    public static void main(String[] args){
        XmlToTxt xmlToTxt = new XmlToTxt();

    }
}
