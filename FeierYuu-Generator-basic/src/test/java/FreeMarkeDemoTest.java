import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: FreeMarkeDemoTest
 * Description:
 * date: 2024/9/9 22:17
 *
 * @author 飞飞鱼
 * @since JDK 1.8
 */
public class FreeMarkeDemoTest {
    @Test
    public  void test1() throws  Exception{
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

// 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

// 设置模板文件使用的字符集
        configuration.setDefaultEncoding("UTF-8");
        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("myweb.html.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear", 2023);
        ArrayList<Object> menuItems = new ArrayList<>();
        Map<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("url", "https://codefather.cn");
        menuItem1.put("label", "编程导航");
        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "https://laoyujianli.com");
        menuItem2.put("label", "老鱼简历");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataModel.put("menuItems", menuItems);


        Writer out = new FileWriter("myweb.html");

        template.process(dataModel, out);

// 生成文件后别忘了关闭哦
        out.close();


    }
}
