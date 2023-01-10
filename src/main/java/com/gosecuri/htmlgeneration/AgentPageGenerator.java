package com.gosecuri.htmlgeneration;

import com.gosecuri.agent.Agent;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static com.gosecuri.utils.PathUtils.*;

public class AgentPageGenerator extends PageGenerator {

    private final Agent agent;

    public AgentPageGenerator(final String outputPath, final Agent _agent) {
        super(outputPath);
        agent = _agent;
    }

    private void addEquipment() {
        boolean isEquipment = false;
        for(String item : agent.getEquipment()) {
            for(Element checkbox : doc.select(".checkbox")) {
                if(checkbox.id().equals(item)) {
                    checkbox.addClass("checked");
                }
            }
        }
    }

    private void addAgentIdentity() {
        doc.getElementById("agent-name").text(agent.getFirstName() + " " + agent.getLastName());
    }

    private String getIdentityCardPath() {
        return (IDENTITY_CARDS_FOLDER_PATH + agent.getUsername() + ".png").replaceAll("\\\\", "/");
    }

    private void addIdentityCard() {
        Element img = doc.select("#identity").first();
        img.attr("src", getIdentityCardPath());
    }

    @Override
    public void generateHTML() throws IOException {
        LoadHTMLTemplateToDocument(AGENT_TEMPLATE_PATH);
        addEquipment();
        addAgentIdentity();
        addIdentityCard();
        File newHtmlFile = new File(outputPath + agent.getUsername() + ".html");
        FileUtils.writeStringToFile(newHtmlFile, doc.toString(), (String) null);
        System.out.println("HTML page created for agent " + agent.getUsername() + " at " + Path.of(outputPath + agent.getUsername() + ".html"));
    }
}