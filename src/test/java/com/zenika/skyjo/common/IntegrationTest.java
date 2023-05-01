package com.zenika.skyjo.common;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@ActiveProfiles({"it"})
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = {MongoDbContainerManager.class})
@Retention(RUNTIME)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Tag("it")
@Target(TYPE)
@TestExecutionListeners(
        mergeMode = MERGE_WITH_DEFAULTS,
        listeners = {
                MongoDbContainerManager.class,
        }
)
public @interface IntegrationTest {
}
