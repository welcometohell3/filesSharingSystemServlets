package com.example.files.model;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final String TEST_USER_NAME = "testuser";
    private static final String TEST_USER_NAME1 = "testuser1";
    private static final String TEST_PASSWORD = "testpassword";
    private static final String TEST_PASSWORD1 = "testpassword1";
    private static final ServletContext mockContext = createMockServletContext();

    @BeforeEach
    void setUp() {
        User.getUserList().clear();
    }

    @BeforeAll
    static void setUpBeforeAll() throws IOException {
        createTestFile();
    }

    @AfterEach
    void tearDown() {
        User.deleteAccount(TEST_USER_NAME, mockContext);
    }

    @AfterAll
    static void tearDownAfterAll() {
        deleteFile();
        String testDirectoryPath = "src/test/resources/uploads";
        Files.deleteDirectory(new File(testDirectoryPath));
    }

    @Test
    void testLoadUsers() {
        createUsersFile();

        User.loadUsers(mockContext);

        assertEquals(2, User.getUserList().size());
        assertTrue(User.getUserList().contains(TEST_USER_NAME));

        String userDirPath = User.getCatalogFilePath(TEST_USER_NAME, mockContext);
        File userDir = new File(userDirPath);
        assertTrue(userDir.exists() && userDir.isDirectory());
        User.deleteAccount(TEST_USER_NAME1, mockContext);
    }

    @Test
    public void testAddUser() {
        User.addUser(TEST_USER_NAME, TEST_PASSWORD, mockContext);
        assertTrue(User.isUserExists(TEST_USER_NAME, mockContext));
    }

    public static void deleteFile() {

        File fileToDelete = new File("src/test/resources/users.txt");

        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    @Test
    void testChangePassword() {
        User.addUser(TEST_USER_NAME, TEST_PASSWORD, mockContext);
        String newPassword = "newtestpassword";
        User.changePassword(TEST_USER_NAME, newPassword, mockContext);
        assertFalse(User.isValidUser(TEST_USER_NAME, TEST_PASSWORD, mockContext));
        assertTrue(User.isValidUser(TEST_USER_NAME, newPassword, mockContext));
    }

    @Test
    void testDeleteAccount() {
        User.addUser(TEST_USER_NAME, TEST_PASSWORD, mockContext);
        User.deleteAccount(TEST_USER_NAME, mockContext);
        assertFalse(User.isUserExists(TEST_USER_NAME, mockContext));
    }

    private static void createTestFile() throws IOException {
        String filePath = "src/test/resources/users.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        }
    }

    void createUsersFile() {
        try (FileWriter writer = new FileWriter(User.getUsersFilePath(mockContext))) {
            writer.write(TEST_USER_NAME + ";" + TEST_PASSWORD + "\n");
            writer.write(TEST_USER_NAME1 + ";" + TEST_PASSWORD1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static ServletContext createMockServletContext() {
        ServletContext mockContext = Mockito.mock(ServletContext.class);

        Mockito.when(mockContext.getRealPath("/WEB-INF/users.txt"))
                .thenReturn("src/test/resources/users.txt");
        Mockito.when(mockContext.getRealPath("/WEB-INF/uploads/"))
                .thenReturn("src/test/resources/uploads/");

        return mockContext;
    }

    @Test
    void testGetUserList() {
        User.getUserList().clear();

        User.getUserList().add("user1");
        User.getUserList().add("user2");

        List<String> result = User.getUserList();
        assertEquals(2, result.size());
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
    }

    @Test
    void testGetUsersFilePath() {
        String result = User.getUsersFilePath(mockContext);
        assertEquals("src/test/resources/users.txt", result);
    }

    @Test
    void testGetCatalogFilePath() {
        String result = User.getCatalogFilePath("testuser", mockContext);
        assertEquals("src/test/resources/uploads/testuser", result);
    }

}