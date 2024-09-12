package com.feieryuu.maker.model;

import lombok.Data;

/**
 * ClassName: MainTemplateConfig
 * Description:
 * date: 2024/9/9 23:07
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@Data
public class DataModel {
//   是否生成循环
    private  boolean loop;
//   作者
    private  String  author;
//  输出信息
    private  String  outputText;
}
