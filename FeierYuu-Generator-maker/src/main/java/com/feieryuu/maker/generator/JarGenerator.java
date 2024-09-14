package com.feieryuu.maker.generator;

import java.io.*;

/**
 * ClassName: JarGenerator
 * Description:
 * date: 2024/9/15 0:24
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public class JarGenerator {
    public  static  void doGenerator(String projectDir) throws IOException, InterruptedException {
        String winMavenCommand="mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand="mvn clean package -DskipTests=true";

        String  mavenCommand=winMavenCommand;

        //用空格拆分
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        //读取命令的输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line= reader.readLine())!=null){
            System.out.println(line);
        }
        //等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码"+exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerator("F:\\JavaCode\\FeierYuu-CodeGenerator\\FeierYuu-Generator-maker\\generated\\acm-template-pro-generator");
    }
}
