package com.feieryuu.maker.meta.enums;

/**
 * ClassName: FileTypeEnum
 * Description:
 * date: 2024/9/19 2:50
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public enum FileTypeEnum {
    DIR("目录", "dir"),

    FILE("文件", "file"),
    GROUP("文件组", "group");

    private final String text;

    private final String value;

    FileTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
