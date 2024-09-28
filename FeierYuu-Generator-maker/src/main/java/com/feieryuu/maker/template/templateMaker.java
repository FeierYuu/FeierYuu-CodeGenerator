package com.feieryuu.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.feieryuu.maker.meta.Meta;
import com.feieryuu.maker.meta.enums.FileGenerateTypeEnum;
import com.feieryuu.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: templateMaker
 * Description:
 * date: 2024/9/28 20:18
 * 模板制作工具
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public class templateMaker {

    /**
     * 制作模板
     *
     * @param newMeta
     * @param originProjectPath
     * @param inputFilePathList
     * @param modelInfo
     * @param searchStr
     * @param id
     * @return
     */
    private static long makeTemplate(Meta newMeta, String originProjectPath, List<String> inputFilePathList, Meta.ModelConfig.ModelInfo modelInfo, String searchStr, Long id) {
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        //业务逻辑

        //复制目录
        String projectPath = System.getProperty("user.dir");
        String tempDirPath = projectPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;

        // 是否为首次制作模板
        // 目录不存在，则是首次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }


        //输入文件信息
        //要挖坑的项目路径
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        //windows 系统 对\\ 转换成 /
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");
        //要挖坑的文件


        ArrayList<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        for (String inputFilePath : inputFilePathList) {
            String inputFileAbsolutePath = sourceRootPath + File.separator + inputFilePath;
            // 输入的是目录
            if (FileUtil.isDirectory(inputFileAbsolutePath)) {
                List<File> fileList = FileUtil.loopFiles(inputFileAbsolutePath);
                for (File file : fileList) {
                    Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(modelInfo, searchStr, sourceRootPath, file);
                    newFileInfoList.add(fileInfo);
                }
            } else {
                // 输入的是文件
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(modelInfo, searchStr, sourceRootPath, new File(inputFileAbsolutePath));
                newFileInfoList.add(fileInfo);
            }
        }


        //生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";
        //已有meta 文件 则不是第一次制作 则在原有基础上修改
        if (FileUtil.exist(metaOutputPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;

            // 追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            //配置文件信息 去重
            oldMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            oldMeta.getModelConfig().setModels(distinctModels(modelInfoList));

        } else {
            //构造配置参数 对象
            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);

            ArrayList<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);


            fileInfoList.addAll(newFileInfoList);


            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            ArrayList<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);


        }
        //输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);

        return id;
    }

    /**
     * 制作模板文件
     *
     * @param modelInfo
     * @param searchStr
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(Meta.ModelConfig.ModelInfo modelInfo, String searchStr, String sourceRootPath, File inputFile) {
        // 要挖坑的文件绝对路径（用于制作模板）
        // 注意 win 系统需要对路径进行转义
        String fileInputAbsolutePath = inputFile.getAbsolutePath().replaceAll("\\\\", "/");
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";


        //注意 一定要是相对路径
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");

        String fileOutputPath = fileInputPath + ".ftl";
        //使用 字符串 替换 生成模板文件
        String fileContent;

        //如果已有模板文件 则不是第一次制作 在原有基础上 在挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }

        String replacement = String.format("${%s}", modelInfo.getFieldName());
        String newContent = StrUtil.replace(fileContent, searchStr, replacement);

        //输出模板文件
        FileUtil.writeUtf8String(newContent, fileOutputAbsolutePath);


        //文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());

        // 和原文件一致，没有挖坑，则为静态生成
        if (newContent.equals(fileContent)) {
            // 输出路径 = 输入路径
            fileInfo.setOutputPath(fileInputPath);
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        } else {
            // 生成模板文件
            fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
            FileUtil.writeUtf8String(newContent, fileOutputAbsolutePath);
        }

        return fileInfo;
    }

    public static void main(String[] args) {

        String name = "acm-template-generator";

        String description = "ACM 示例模板生成器";


        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = new File(projectPath).getParent() + File.separator + "FeierYuu-CodeGenerator-demoProject/springboot-init";
        String inputFilePath1 = "src/main/java/com/yupi/project";
        List<String> inputFilePathList = Arrays.asList(inputFilePath1);

        // 模型参数信息（首次）
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");

        // 模型参数信息（第二次）
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

        // 替换变量（首次）
//        String searchStr = "Sum: ";
        // 替换变量（第二次）
        String searchStr = "BaseResponse";

        long id = makeTemplate(meta, originProjectPath, inputFilePathList, modelInfo, searchStr, 1735281524670181376L);
        System.out.println(id);
    }


    /**
     * 文件去重
     *
     * @param fileInfoList
     * @return
     */
    private static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList) {
        ArrayList<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(fileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (old, news) -> news)
                ).values());
        return newFileInfoList;
    }


    /**
     * 模型去重
     */

    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        ArrayList<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(modelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (old, news) -> news)
                ).values());
        return newModelInfoList;
    }

}
