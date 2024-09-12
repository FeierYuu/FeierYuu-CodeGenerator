package com.feieryuu.maker.generator.file;

import cn.hutool.core.io.FileUtil;

/**
 * ClassName: StaticGenerator
 * Description:
 * date: 2024/9/9 4:51
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public class StaticFileGenerator {


    /**
     *
     * @param inputPath 输入路径
     * @param outPutPath 输出路径
     */
    public  static void CopyFileByHutool(String inputPath, String outPutPath){
        FileUtil.copy(inputPath,outPutPath,false);
    }



}
