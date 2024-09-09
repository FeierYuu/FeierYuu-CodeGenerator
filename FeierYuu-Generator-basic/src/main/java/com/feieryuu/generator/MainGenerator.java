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
    public static void main(String[] args)throws Exception {
        String projectPath = System.getProperty("user.dir");
        // 输入路径
        String inputPath = projectPath + File.separator + "FeierYuu-CodeGenerator-demoProject" + File.separator + "acm-template";
        // 输出路径
        String outputPath = projectPath;

        StaticGenerator.copyFilesByRecursive(inputPath, outputPath);



        String DynamicInputPath = projectPath+ File.separator+"FeierYuu-Generator-basic" + File.separator + "src/main/resources/templates/MyTemplate.java.ftl";
        String DynamicOutputPath = projectPath + File.separator + "acm-template/src/com/yupi/acm/MainTemplate.java";
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("FeierYuu");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("结果：");
        DynamicGenerator.doGenerate(DynamicInputPath, DynamicOutputPath, mainTemplateConfig);
    }
}
