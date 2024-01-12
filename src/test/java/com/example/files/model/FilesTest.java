package com.example.files.model;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilesTest {

    private static final String TEST_USER_NAME = "vasya";
    private static final String TEST_FILE_NAME = "testfile.txt";

    private final ServletContext mockContext = mock(ServletContext.class);

    @BeforeEach
    void setUp() {
        when(mockContext.getRealPath("/WEB-INF/uploads/")).thenReturn("src/test/resources/uploads/");
        createTestDirectory(TEST_USER_NAME);
    }

    @AfterAll
    static void cleanUp() {
        String testDirectoryPath = "src/test/resources/uploads";
        Files.deleteDirectory(new File(testDirectoryPath));
    }

    void createTestDirectory(String userName) {
        String directoryPath = User.getCatalogFilePath(userName, mockContext);
        File directory = new File(directoryPath);
        directory.mkdirs();
    }

    @Test
    void testDownloadFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        createTestFile(TEST_USER_NAME, TEST_FILE_NAME);
        File sourceFile = new File("src/test/resources/uploads/vasya/testfile.txt");
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write("aboba123");
        }
        Files.downloadFile(TEST_USER_NAME, TEST_FILE_NAME, outputStream, mockContext);
        assertEquals("aboba123", outputStream.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void testDeleteFile() throws IOException {
        String testFileName = "testfile.txt";

        createTestFile(TEST_USER_NAME, testFileName);

        Files.deleteFile(TEST_USER_NAME, testFileName, mockContext);

        File userDirectory = new File(User.getCatalogFilePath(TEST_USER_NAME, mockContext));
        assertTrue(userDirectory.exists());

        File deletedFile = new File(userDirectory, testFileName);
        assertFalse(deletedFile.exists());
    }

    private void createTestFile(String userName, String fileName) throws IOException {
        String filePath = User.getCatalogFilePath(userName, mockContext) + File.separator + fileName;
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        }
    }

    @Test
    void testExtractFileName() {
        Part mockPart = mock(Part.class);
        when(mockPart.getHeader("content-disposition")).thenReturn("form-data; name=\"file\"; filename=\"testfile.txt\"");

        String extractedFileName = Files.extractFileName(mockPart);

        assertEquals("testfile.txt", extractedFileName);
    }

    @Test
    void testDeleteAllUserFiles() {
        Files.deleteAllUserFiles(TEST_USER_NAME, mockContext);

        File userDir = new File("src/test/resources/uploads/vasya");
        assertFalse(userDir.exists());
    }

    @Test
    void testCopyFile() throws IOException {
        String targetUserName = "kolya";
        File targetFile = new File("src/test/resources/uploads/kolya/testfile.txt");
        File sourceFile = new File("src/test/resources/uploads/vasya/testfile.txt");
        createTestDirectory(targetUserName);
        createTestFile(TEST_USER_NAME, TEST_FILE_NAME);
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write("aboba123");
        }
        Files.shareFile(TEST_USER_NAME, targetUserName, TEST_FILE_NAME, mockContext);

        assertTrue(targetFile.exists());
        assertEquals("aboba123", readFileContent(targetFile));
    }

    private String readFileContent(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                result.write(buffer, 0, bytesRead);
            }
            return result.toString(StandardCharsets.UTF_8);
        }
    }

}
