package com.gosecuri.security;

import com.gosecuri.agent.Agent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HtpasswdGenerator {

    private final String outputPath;
    private final List<Agent> agents;

    public HtpasswdGenerator(final String _outputPath, List<Agent> _agents) {
        outputPath = _outputPath;
        agents = _agents;
    }

    public void generateHtpasswd() throws IOException {
        StringBuilder content = new StringBuilder();

        for(Agent agent : agents) {
            content.append(agent.getUsername()).append(":").append(agent.getEncryptedPassword()).append("\n");
        }

        File htpasswd = new File(outputPath + "agents.htpasswd");
        FileUtils.writeStringToFile(htpasswd, content.toString(), (String) null);
        System.out.println(".htpasswd generated");
    }
}
