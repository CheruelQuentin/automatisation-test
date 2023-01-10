package com.gosecuri.htmlgeneration;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.gosecuri.utils.PathUtils.AGENT_TEMPLATE_PATH;

public abstract class PageGenerator {

    protected final String outputPath;
    protected Document doc;

    public PageGenerator(final String _outputPath) {
        outputPath = _outputPath;
    }

    public abstract void generateHTML() throws IOException;

    public void LoadHTMLTemplateToDocument(final String templatePath) throws IOException {
        File htmlTemplateFile = new File(templatePath);
        String htmlString = FileUtils.readFileToString(htmlTemplateFile, StandardCharsets.UTF_8);
        doc = Jsoup.parse(htmlString);
    }

}
