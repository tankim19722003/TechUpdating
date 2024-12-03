package com.techupdating.techupdating.controllers.commons;

import java.io.File;
import java.net.URL;

public class TestClass {
    public static void main(String[] args) {
        // Use a directory where the file can be modified (e.g., /target/uploads)
        String filePath = "target/uploads/1a160e60-b412-48f2-801a-98a953183b47_house.png";

        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

}
