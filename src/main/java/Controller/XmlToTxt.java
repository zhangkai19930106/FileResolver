package Controller;

import Utils.FileUtils;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KayZq on 2016/12/6.
 */
public class XmlToTxt {
    private String originalPathAndFileName;     //原始未处理过路径+文件名。例如：C:\Users\KayZq\Desktop\2016112051_SHEETS_3.xml
    private String dealPath;             //处理后的路径，可以用户指定，否则在原来的路径下生成文件
    private String separate="|";        //分隔符号

    /**
     * 两个不同参数的构造函数，初始化变量
     * */
    public XmlToTxt(String originalPathAndFileName,String dealPath){
        this.originalPathAndFileName = originalPathAndFileName;
        this.dealPath = dealPath;
        this.dealPath=FileUtils.getFileNameNoEx(originalPathAndFileName);                   //将文件名传入并进行处理
    }
    public XmlToTxt(String originalPathAndFileName){
        this.originalPathAndFileName = originalPathAndFileName;
        String fileName = originalPathAndFileName.substring(originalPathAndFileName.lastIndexOf("\\"), originalPathAndFileName.lastIndexOf("."));
        this.dealPath = originalPathAndFileName.substring(0,originalPathAndFileName.lastIndexOf("\\"))+"\\dealt"+fileName+".txt";//用户没有输入处理后的路径,则在原始路径下生成一个dealt文件夹保存
        FileUtils.createFolder(originalPathAndFileName);          //创建文件夹
    }
    /**
     * operation函数是进行xml解析的函数
     * 这个函数首先会处理原始文件名字，生成处理后文件的路径和名字
     * 然后将处理后的结果，输出到dealPath这个路径
     * */
    public void operation()throws Exception{
        long StartTime = System.currentTimeMillis();                //获取操作开始时间
        BufferedReader bufferedReader = new BufferedReader(new FileReader(originalPathAndFileName));  //定义一个带缓冲的字节输入流
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dealPath));  //定义一个带缓冲的字节输出流
        String b;
        int retractFlag=-2;    //缩进flag
        boolean newline=false; //换行flag
        String headTag=null;   //暂存标签头
        String content=null;   //暂存标签体
        boolean head_find;     //匹配到标签头
        boolean content_find;  //匹配到标签体
        boolean tail_find;     //匹配到标签尾
        Pattern content_pattern = Pattern.compile(">([^<]+)");
        Pattern head_pattern = Pattern.compile("<([^>|^/]+)>");
        Pattern tail_pattern = Pattern.compile("</([^>]+)>");

        try{
            while((b=bufferedReader.readLine())!=null){                    //如果没有复制完就循环
                Matcher head_matcher = head_pattern.matcher(b);
                Matcher content_matcher = content_pattern.matcher(b);
                Matcher tail_matcher = tail_pattern.matcher(b);

                head_find=head_matcher.find();
                content_find=content_matcher.find();
                tail_find=tail_matcher.find();

                if(head_find && tail_find && content_find==false){      //匹配到空标签，也即只有标签头和尾，没有标签体
                    continue;
                }else if(head_find && tail_find){                       //匹配到正常标签
                    if(newline==true){
                        for(int i=0;i<=retractFlag-1;i++){              //缩进
                            bufferedWriter.write("   ");
                        }
                        newline=false;
                        bufferedWriter.write(content_matcher.group(1));         //将提取的内容写入文件
                        bufferedWriter.write(separate);                 //将用户输入的分隔符写入文件
                    }else{
                        bufferedWriter.write(content_matcher.group(1));         //将提取的内容写入文件
                        bufferedWriter.write(separate);                 //将用户输入的分隔符写入文件
                    }
                }else if(head_find && content_find){   //处理标签头和尾不在一行的情况
                    headTag = head_matcher.group(1);
                    content = content_matcher.group(1);
                    if(newline==false){
                        bufferedWriter.newLine();
                        newline=true;
                    }
                }else if(head_find && content_find==false){             //匹配标签头，处理缩进
                    retractFlag=retractFlag+1;
                    if(newline==false){
                        bufferedWriter.newLine();
                        newline=true;
                    }
                }else if(tail_find && content_find==false){             //匹配到标签尾，处理缩进
                    if(headTag!=null && headTag.equals(tail_matcher.group(1))){
                        for(int i=0;i<=retractFlag-1;i++){
                            bufferedWriter.write("   ");
                        }
                        bufferedWriter.write(content+"\r");
                    }else{
                        retractFlag-=1;
                        if(newline==false){
                            bufferedWriter.newLine();
                            newline=true;
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }finally {
            bufferedReader.close();                 //关闭流
            bufferedWriter.close();
        }
        long EndTime = System.currentTimeMillis();                    //获取操作结束时间
        System.out.println("未处理之前的文件: "+originalPathAndFileName);
        System.out.println("处理之后的文件: "+dealPath + "   Time:" + (EndTime - StartTime) + " ms");
    }
    public static void main(String[] args){
        System.out.println("输入要解析的文件路径(文件所在的根目录)：");
        Scanner scan = new Scanner(System.in);
        String location = scan.nextLine();
        try{
            List<String> filePathAndFileName = FileUtils.findByEx("xml", location);
            for(String f:filePathAndFileName){
                XmlToTxt xmlToTxt = new XmlToTxt(f);
                xmlToTxt.operation();
            }
        }catch (Exception e){
            System.out.println("系统找不到指定的文件,请检查一下路径是否有误!");
        }
    }
}
