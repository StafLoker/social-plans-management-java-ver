package org.stafloker.data.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

@SpringBootTest
class UserTest {

    @Test
    void setPassword() {
        assertThrows(InvalidAttributeException.class, () -> this.repositorioUsuarios.read(1).get().setContrasena("12");
    }

    @Test
    void setAge() {
        assertThrows(InvalidAttributeException.class, () -> this.repositorioUsuarios.read(1).get().setEdad(101);
    }

}