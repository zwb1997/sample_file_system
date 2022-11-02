package com.filesystem;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.filesystem.services.CustomerService;
import com.filesystem.services.DocumentService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class })
public class AppTest {

    private static final Logger LOG = LogManager.getLogger(AppTest.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DocumentService documentService;

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue("wow!", true);
    }

    @Test
    public void createFile() {
        File file = new File("demo.txt");

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
        try (var f = new FileInputStream(new File("demo.tgxt"))) {
            System.out.println("succeed");
        } catch (Exception e) {
            System.out.println("failed message:" + e.getMessage());
        }
    }

    @Test
    public void testCreateFileWithPrivilege() {

        try (FileOutputStream fos = new FileOutputStream(new File("D:\\demo.txt"))) {
            String words = "hello!";
            fos.write(words.getBytes());
            fos.flush();
        } catch (Exception ex) {
            System.out.println(String.format("create file demo.txt error, message:[%s]", ex.getMessage()));
        }
    }

    @Test
    public void testTransactionService() {

    }
}
