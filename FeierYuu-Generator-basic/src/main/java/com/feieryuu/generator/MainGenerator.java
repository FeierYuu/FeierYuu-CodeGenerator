package com.feieryuu.generator;

import com.feieryuu.model.MainTemplateConfig;

import java.io.File;

/**
 * ClassName: MainGenerator
 * Description:
 * date: 2024/9/10 0:40
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public class MainGenerator {


    public static void doGenerate(Object model)throws Exception{
        String projectPath = System.getProperty("user.dir");

        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile,"FeierYuu-CodeGenerator-demoProject/acm-template").getAbsolutePath();
        // 输出路径
        String outputPath = projectPath;

        StaticGenerator.copyFilesByRecursive(inputPath, outputPath);



        String DynamicInputPath = projectPath+ File.separator+"src/main/resources/templates/MyTemplate.java.ftl";
        String DynamicOutputPath = projectPath + File.separator + "acm-template/src/com/yupi/acm/MainTemplate.java";

        DynamicGenerator.doGenerate(DynamicInputPath, DynamicOutputPath, model);
    }


    public static void main(String[] args)throws Exception {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("3213");
        mainTemplateConfig.setLoop(true);
        mainTemplateConfig.setOutputText("结果");
        doGenerate(mainTemplateConfig);
    }


}
