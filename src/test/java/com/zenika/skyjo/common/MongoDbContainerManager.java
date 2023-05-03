package com.zenika.skyjo.common;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * A {@link TestExecutionListener} to launch one {@link MongoDbContainerManager} for all integration tests.
 * <a href="https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/">Contrôler le cycle de vie</a>
 */
public class MongoDbContainerManager implements TestExecutionListener, ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MongoDBContainer mongoDbContainer;
    public static final String MONGODB_TAG = "6";
    private static final String dbName = "skyjo";

    static {
        mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:" + MONGODB_TAG))
                .withClasspathResourceMapping("js/mongo-init-user.js",
                        "/docker-entrypoint-initdb.d/mongo-init-user.js",
                        BindMode.READ_ONLY)
                // https://www.testcontainers.org/features/reuse/
                .withReuse(true);
        // Demarrer
        mongoDbContainer.start();
    }

    @Override
    public void beforeTestMethod(@NonNull TestContext testContext) {
        MongoTemplate mongo = testContext.getApplicationContext().getBean(MongoTemplate.class);
        mongo.getCollectionNames().forEach(mongo::dropCollection);
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext ac) {
        // Ajout de la bonne propriété pour surcharger la configuration
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                ac, "spring.data.mongodb.uri=" + mongoDbContainer.getReplicaSetUrl(dbName));
    }

}
