package com.feieryuu.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.feieryuu.maker.generator.file.DynamicFileGenerator;
import com.feieryuu.maker.meta.Meta;
import com.feieryuu.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;


public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        // 输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }

        //复制从原始模板文件 复制到生成的代码包中
        String sourceRootPath=meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath=outputPath + File.separator+".source";
        FileUtil.copy(sourceRootPath, sourceCopyDestPath, true);


        // 读取 resources 目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();

        // Java 包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        // model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GeneratorCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GeneratorCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);


        //pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath +File.separator+ "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        //README.md
        inputFilePath = inputResourcePath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath +File.separator+ "README.md";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        //生成脚本文件
        String shellOutPutFilePath= outputPath + File.separator + "generator";
        String jarName=String.format("%s-%s-jar-with-dependencies.jar",meta.getName(),meta.getVersion());
        String jarPath="target/"+jarName;
        ScriptGenerator.doGenerate(shellOutPutFilePath, jarPath);


        //生成精简的代码生成包--dist
        String distOutPutPath=outputPath+"-dist";
        //拷贝jar包
        String targetAbsolutePath= distOutPutPath+File.separator+"target";
        if (FileUtil.exist(targetAbsolutePath)){
            FileUtil.mkdir(targetAbsolutePath);
        }
        String jarAbsolutePath=outputPath+File.separator+jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath,true);

        //拷贝脚本文件
        FileUtil.copy(shellOutPutFilePath, distOutPutPath,true);
        FileUtil.copy(shellOutPutFilePath+".bat", distOutPutPath,true);

        //拷贝模板文件
        FileUtil.copy(sourceCopyDestPath, distOutPutPath,true);


    }
}
