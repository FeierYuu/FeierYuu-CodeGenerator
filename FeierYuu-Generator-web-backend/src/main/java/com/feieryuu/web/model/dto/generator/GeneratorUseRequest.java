package com.feieryuu.web.model.dto.generator;

import com.feieryuu.web.meta.Meta;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 使用代码生成器请求
 *
 * @TableName product
 */
@Data
public class GeneratorUseRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 数据模型
     */
    private Map<String, Object> dataModel;

    private static final long serialVersionUID = 1L;
}