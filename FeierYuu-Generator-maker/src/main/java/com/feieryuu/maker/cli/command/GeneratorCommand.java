//package com.feieryuu.maker.cli.command;
//
//
//import cn.hutool.core.bean.BeanUtil;
//import com.feieryuu.maker.generator.file.FileGenerator;
//import lombok.Data;
//import picocli.CommandLine;
//
//import java.util.concurrent.Callable;
//
//
///**
// * ClassName: GeneratorCommand
// * Description:
// * date: 2024/9/11 3:57
// *
// * @author 飞飞鱼
// * @since JDK 1.8
// */
//
//@Data
//@CommandLine.Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
//public class GeneratorCommand implements Callable<Integer> {
//    //   是否生成循环
//    @CommandLine.Option(names = {"-l", "--loop"}, arity = "0..1", echo = true, description = "是否循环", interactive = true)
//    private boolean loop;
//    //   作者
//    @CommandLine.Option(names = {"-a", "--author"}, arity = "0..1", echo = true, description = "作者名", interactive = true)
//    private String author;
//    //  输出信息
//    @CommandLine.Option(names = {"-o", "--outputText"}, arity = "0..1", echo = true, description = "输出文本", interactive = true)
//    private String outputText;
//
//
//    @Override
//    public Integer call() throws Exception {
//        DataModel dataModel = new DataModel();
//        BeanUtil.copyProperties(this, dataModel);
//        System.out.println("配置信息：" + dataModel);
//        FileGenerator.doGenerate(dataModel);
//        return 0;
//    }
//}
//
