package com.feieryuu.maker.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.ListUI;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

/**
 * ClassName: Meta
 * Description:
 * date: 2024/9/13 5:01
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@Data
public class Meta {
    private String name;
    private String description;
    private String basePackage;
    private String version;
    private String author;
    private String createTime;
    private FileConfig fileConfig;
    private ModelConfig modelConfig;

    @Data
    @NoArgsConstructor
    public static class FileConfig{
        private String inputRootPath;
        private String outputRootPath;
        private String type;
        private List<FileInfo> files;
    }
    @Data
    @NoArgsConstructor
    public static class FileInfo {
        private String inputPath;
        private String outputPath;
        private String type;
        private String generateType;
    }
    @Data
    @NoArgsConstructor

    public static class ModelInfo{
        private String fieldName;
        private String type;
        private String description;
        private Object defaultValue;
        private String abbr;
    }
    @Data
    @NoArgsConstructor
    public static class ModelConfig {
        private List<ModelInfo> models;
    }
}
