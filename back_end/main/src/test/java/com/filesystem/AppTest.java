package com.filesystem;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Test;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue("wow!", true);
    }

    @Test
    public void createFile() {
        File file = new File("D:\\demo.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("file created");
            }

        } catch (Exception e) {
            System.err.println(String.format("create file error, message:[%s]", e.getMessage()));
        }

    }

    @Test
    public void createFileByFileSteam() throws Exception {
        try (var f = new FileInputStream(new File("c:\\demo.tgxt"))) {
            System.out.println("succeed");
        } catch (Exception e) {
            System.out.println("failed message:" + e.getMessage());
        }
    }

    @Test
    public void testCreateFileWithPrivilege() {

        try (FileOutputStream fos = new FileOutputStream(new File("C:\\demo.txt"))) {
            String words = "hello!";
            fos.write(words.getBytes());
            fos.flush();
        } catch (Exception ex) {
            System.out.println(String.format("create file demo.txt error, message:[%s]", ex.getMessage()));
        }
    }

}
