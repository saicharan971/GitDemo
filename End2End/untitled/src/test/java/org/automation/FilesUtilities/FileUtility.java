package org.automation.FilesUtilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileUtility {



    @DataProvider(name = "jsonDataProvider")
    public Object[][] jsonDataProvider() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Map<String, Object>>> root = mapper.readValue(
                new File("d:/RestAssured/End2End/untitled/src/main/resources/TestData.json"),
                new TypeReference<Map<String, List<Map<String, Object>>>>() {}
        );
        List<Map<String, Object>> data = root.get("testUsers");


            Object[][] result = new Object[data.size()][1];
            for (int i = 0; i < data.size(); i++) {
                result[i][0] = data.get(i);
            }
            return result;
        }


    }

