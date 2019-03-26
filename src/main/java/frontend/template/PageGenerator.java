package frontend.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;



public class PageGenerator {

    private static final String HTML_DIR = "res_html";

    private static PageGenerator pageGenerator;
    private final Configuration configuration;


    private PageGenerator() {
        configuration = new Configuration();
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(HTML_DIR + File.separator + filename, "utf-8");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }


    public String getPageSingleField (String filename, String fieldName, String value) {
        Map<String, Object> data = new HashMap<>();
        data.put(fieldName, value);
        return getPage(filename, data);
    }





}
