package application.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.html2pdf.HtmlConverter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CV {

    public static void bakeCV(Human human, String destination) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        String currentDirPath = System.getProperty("user.dir");
        File currentDir = new File(currentDirPath);
        cfg.setDirectoryForTemplateLoading(currentDir);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        Map<String, Object> valueMap = new HashMap<>();
        File profileImage = human.getProfileImage();
        if (profileImage != null) {
            String profileImageUri = convertPathToUri(profileImage.getCanonicalPath());
            valueMap.put("profilePicture", profileImageUri.toString());
        }
        valueMap.put("fullName", human.getFullName());
        if (human.getPhone() != "") {
            valueMap.put("phone", human.getPhone());
        }
        if (human.getEmail() != "") {
            valueMap.put("email", human.getEmail());
        }
        valueMap.put("study", human.getStudy());
        valueMap.put("skills", human.getSkills());

        Map<String, String> languageLevelMap = new HashMap<>();
        for(Language language : human.getLanguages()) {
            languageLevelMap.put(language.getLanguage(), language.getLevel());
        }
        valueMap.put("languages", languageLevelMap);
        valueMap.put("experiences", human.getExperience());

        Template template = cfg.getTemplate("\\src\\template\\cv_template.ftl");
        String outputPath = currentDirPath + "\\src\\tmp\\cv_output.html";
        File tempOutput = new File(outputPath);
        Writer output = new FileWriter(tempOutput);
        template.process(valueMap, output);
        output.flush();
        output.close();
        HtmlConverter.convertToPdf(new File(outputPath), new File(destination));
        tempOutput.delete();
    }

    protected static String convertPathToUri(String filePathString) {
        Path filePath = Paths.get(filePathString);
        URI fileUri = filePath.toUri();
        return fileUri.toString();
    }

    protected static String retrieveHtml(String htmlPath) {
        try {
            StringBuilder html = new StringBuilder();
            FileReader fr = new FileReader(htmlPath);
            BufferedReader br = new BufferedReader(fr);
            String val;
            while ((val = br.readLine()) != null) {
                html.append(val);
            }
            br.close();
            return html.toString();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

}
