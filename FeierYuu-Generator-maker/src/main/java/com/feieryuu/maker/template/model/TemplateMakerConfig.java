package com.feieryuu.maker.template.model;

import com.feieryuu.maker.meta.Meta;
import lombok.Data;

/**
 * ClassName: TemplateMakerConfig
 * Description:
 * date: 2024/10/3 0:06
 * 模板制作配置
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@Data
public class TemplateMakerConfig {
    private Long id;

    private Meta meta = new Meta();

    private String originProjectPath;

    private TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    private TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    private TemplateMakerOutputConfig templateMakerOutputConfig = new TemplateMakerOutputConfig();
}
