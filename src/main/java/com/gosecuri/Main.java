package com.gosecuri;

import com.gosecuri.agent.Agent;
import com.gosecuri.htmlgeneration.AgentPageGenerator;
import com.gosecuri.htmlgeneration.IndexPageGenerator;
import com.gosecuri.security.HtpasswdGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.gosecuri.utils.PathUtils.*;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        //Set output path of generated files
        String outputPathTemp = GENERATED_FOLDER_PATH;
        if(args.length > 0) {
            String argPath = args[0];
            if(!argPath.endsWith("/")) outputPathTemp = argPath + "/";
            else outputPathTemp = argPath;
        }

        final String outputPath = outputPathTemp;

        //Create list of Agent
        List<Agent> agents = new ArrayList<>();
        File agentFilesFolder = new File(AGENT_TEXT_FILES_FOLDER_PATH);
        String[] files = agentFilesFolder.list();
        if(files != null && files.length > 0) {
            for (String file : files) {
                List<String> agentData = Files.readAllLines(Path.of(AGENT_TEXT_FILES_FOLDER_PATH + file));
                agents.add(new Agent(agentData));
            }
        }

        Thread agentsPagesThread = new Thread(() -> {
            //Generate agents html pages
            for(Agent agent : agents) {
                AgentPageGenerator apg = new AgentPageGenerator(outputPath, agent);
                try {
                    apg.generateHTML();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        agentsPagesThread.start();

        Thread indexPageThread = new Thread(() -> {
            //Generate index.html
            IndexPageGenerator idp = new IndexPageGenerator(outputPath, agents);
            try {
                idp.generateHTML();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        indexPageThread.start();

        Thread htpasswdThread = new Thread(() -> {
            //Generate .htpasswd
            HtpasswdGenerator htpasswdGenerator = new HtpasswdGenerator(outputPath, agents);
            try {
                htpasswdGenerator.generateHtpasswd();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        htpasswdThread.start();
    }
}