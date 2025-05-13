package com.example.accessingdatarest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final ObjectMapper objectMapper;

    public DatabaseSeeder(ProjectRepository projectRepository, ObjectMapper objectMapper) {
        this.projectRepository = projectRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running DatabaseSeeder...");

        projectRepository.deleteAll();

        // Load JSON file
        try (InputStream inputStream = new ClassPathResource("projects.json").getInputStream()) {
            Project[] projects = objectMapper.readValue(inputStream, Project[].class);
            for (Project project : projects) {
                projectRepository.save(project);
            }
            System.out.println("Database seeded with projects from JSON file.");
        } catch (Exception e) {
            System.err.println("Failed to seed database: " + e.getMessage());
        }
    }
}