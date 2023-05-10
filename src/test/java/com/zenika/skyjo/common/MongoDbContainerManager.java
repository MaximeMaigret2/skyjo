package com.zenika.skyjo.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@link TestExecutionListener} to launch one {@link MongoDbContainerManager} for all integration tests.
 * <a href="https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/">Contrôler le cycle de vie</a>
 */
@Sql
public class MongoDbContainerManager implements TestExecutionListener, ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MongoDBContainer mongoDbContainer;
    public static final String MONGODB_TAG = "6";
    private static final String dbName = "skyjo";

    static {
        mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:" + MONGODB_TAG))
                .withClasspathResourceMapping("mongodb/mongo-init-user.js",
                        "/docker-entrypoint-initdb.d/mongo-init-user.js",
                        BindMode.READ_ONLY)
                .withStartupAttempts(10)
                // https://www.testcontainers.org/features/reuse/
                .withReuse(true);
        // Demarrer
        mongoDbContainer.start();
    }

    @Override
    public void beforeTestMethod(@NonNull TestContext testContext) {
        MongoTemplate mongo = testContext.getApplicationContext().getBean(MongoTemplate.class);
        mongo.getCollectionNames().forEach(mongo::dropCollection);
        // Execute MongoDbCommands
        Optional.ofNullable(testContext.getTestMethod().getAnnotation(MongoDbCommand.class))
                .map(mongoDbCommand -> Stream.concat(Arrays.stream(mongoDbCommand.commands()), Arrays.stream(mongoDbCommand.value()))
                        .map(MongoDbContainerManager::readFileFromPath).collect(Collectors.toSet()))
                .ifPresent(commands -> commands.forEach(mongo::executeCommand));
    }

    public static String readFileFromPath(String path) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + path), StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to access file", ioException);
        }
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext ac) {
        // Ajout de la bonne propriété pour surcharger la configuration
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                ac, "spring.data.mongodb.uri=" + mongoDbContainer.getReplicaSetUrl(dbName));
    }

}
