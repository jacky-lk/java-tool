package io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 参考：
 * https://github.com/google/guava/wiki/IOExplained
 * Guava methods that take a stream do not close the stream:
 * closing streams is generally the responsibility of the code that opens the stream
 * guava 不需要主动close stream
 *
 * @author 陆昆
 **/
public class IOSource {
    /**
     * guava sources are readable
     * 读取一个文件
     *
     * @param path
     * @return
     */
    public static ImmutableList<String> readFile(String path) {
        ImmutableList<String> lines = null;
        CharSource charSource = Files.asCharSource(new File(path), Charsets.UTF_8);
        if (charSource != null) {
            try {
                lines = charSource.readLines();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * guava sinks are writable
     * content写到指定文件路径
     *
     * @param path
     * @param content
     */
    public static void writeFile(String path, String content) {
        CharSink charSink = Files.asCharSink(new File(path), Charsets.UTF_8, FileWriteMode.APPEND);
        try {
            charSink.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分行写入文件
     *
     * @param path
     * @param content
     */
    public static void writeFile(String path, List<String> content) {
        CharSink charSink = Files.asCharSink(new File(path), Charsets.UTF_8, FileWriteMode.APPEND);
        try {
            charSink.writeLines(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String path = "/Users/lukun/test-data/test-data.txt";
        String content = "test";
        // 写入测试字符串
        writeFile(path, content);
        List<String> contentLines = Lists.newArrayList();
        for (int i = 0;i < 5;i++) {
            contentLines.add(Integer.toString(i));
        }
        writeFile(path, contentLines);
        // 读取文件
        ImmutableList<String> lines = readFile(path);
        if (CollectionUtils.isNotEmpty(lines)) {
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }
}
