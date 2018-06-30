package com.thinkgem.jeesite.modules.mobile.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 刘智科
 * @Title: $file_name
 * @Package $package_name
 * @Description: $todo
 */
public class Test {

    public static File generateNewText(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
        return file;
    }
}
