package com.feieryuu.maker.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.feieryuu.maker.model.DataModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * ClassName: ConfigCommand
 * Description:
 * date: 2024/9/11 3:56
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
@CommandLine.Command(name = "config",description = "查看参数信息",mixinStandardHelpOptions = true)

public class ConfigCommand implements Runnable{

    /**
     * 通过反射获取信息
     */
    @Override
    public void run() {
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for (Field field : fields) {
            System.out.println("字段名称"+field.getName());
            System.out.println("字段类型"+field.getType());
            System.out.println("---------");
        }
    }
}
