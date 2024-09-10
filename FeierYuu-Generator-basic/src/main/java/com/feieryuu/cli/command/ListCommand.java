package com.feieryuu.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * ClassName: ListCammand
 * Description:
 * date: 2024/9/11 3:56
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@CommandLine.Command(name = "list",description = "查看文件列表",mixinStandardHelpOptions = true)
public class ListCommand implements Runnable{


    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
//        项目根路径
        String parentPath = new File(projectPath).getParent();

        String inputPath = new File(projectPath, "FeierYuu-CodeGenerator-demoProject/acm-template").getAbsolutePath();

        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}
