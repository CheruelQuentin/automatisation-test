package com.gosecuri.utils;

public final class PathUtils {

    public static final String DIR_PATH = System.getProperty("user.dir");
    public static final String BASE_PATH = DIR_PATH + "/src/main/java/com/gosecuri";

    public static final String AGENT_TEMPLATE_PATH = BASE_PATH + "/html/templates/agent.html";
    public static final String INDEX_TEMPLATE_PATH = BASE_PATH + "/html/templates/index.html";

    public static final String GENERATED_FOLDER_PATH = BASE_PATH + "/html/generated/";
    public static final String IDENTITY_CARDS_FOLDER_PATH = "../assets/identitycards/";
    public static final String AGENT_TEXT_FILES_FOLDER_PATH = BASE_PATH + "/textfiles/agentfiles/";

    public static final String STAFF_FILE_PATH = BASE_PATH + "/textfiles/staff.txt";

    public static final String HTACCESS_TEMPLATE_PATH = BASE_PATH + "/security/.htaccess";

    private PathUtils() { }
}
