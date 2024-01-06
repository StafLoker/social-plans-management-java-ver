package org.stafloker;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE) // indicates that this annotation can be applied to types (classes, interfaces, enumerations)
@Retention(RetentionPolicy.RUNTIME) // indicates that the annotation will be available at runtime

@ExtendWith(SpringExtension.class) // indicates that JUnit5 uses Spring Boot
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
public @interface TestConfig {
}
