package com.feieryuu.web.model.dto.generator;

import com.feieryuu.maker.meta.Meta;
import lombok.Data;


/**
 * ClassName: GeneratorMakeRequest
 * Description: 制作代码生成器请求
 * date: 2024/10/20 21:43
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@Data
public class GeneratorMakeRequest {
    /**
     * 元数据信息
     */
    private Meta meta;

    /**
     * 模板文件压缩包路径
     */
    private String zipFilePath;
    private static final long serialVersionUID = 1L;
}
