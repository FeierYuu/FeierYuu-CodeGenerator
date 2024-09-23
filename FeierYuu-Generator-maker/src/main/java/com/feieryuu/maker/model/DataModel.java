package com.feieryuu.maker.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ClassName: MainTemplateConfig
 * Description:
 * date: 2024/9/9 23:07
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@Data
@Accessors(fluent = true)
public class DataModel {
//   是否生成循环
    private  boolean loop;
//   作者
    private  String  author;
//  输出信息
    private  String  outputText;
}
