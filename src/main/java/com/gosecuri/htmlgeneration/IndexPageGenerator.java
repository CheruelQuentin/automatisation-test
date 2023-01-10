package com.gosecuri.htmlgeneration;

import com.gosecuri.agent.Agent;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.gosecuri.utils.PathUtils.*;

public class IndexPageGenerator extends PageGenerator {

    private final List<Agent> agents;

    public IndexPageGenerator(final String _outputPath, final List<Agent> _agents) {
        super(_outputPath);
        agents = _agents;
    }

    private void addAgentLinks() {
        for(Agent agent : agents) {
            Element agentAnchor = doc.select(String.format("#%s", agent.getUsername())).first();
            if(agentAnchor != null) {
                agentAnchor.attr("href", agent.getUsername() + ".html");
                agentAnchor.removeClass("disabled");
            }
        }
    }

    @Override
    public void generateHTML() throws IOException {
        LoadHTMLTemplateToDocument(INDEX_TEMPLATE_PATH);
        addAgentLinks();
        File newHtmlFile = new File(outputPath + "index.html");
        FileUtils.writeStringToFile(newHtmlFile, doc.toString(), (String) null);
        System.out.println("index.html created, added links for " + agents.size() + " agents");
    }
}
