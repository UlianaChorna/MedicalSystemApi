package com.uliana.MedicalSystemApi;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestContainerTest extends  AbstractTestContainers {
    @Test
    void testDBStarted() {
        assertThat(MY_SQL_CONTAINER.isCreated());
        assertThat(MY_SQL_CONTAINER.isRunning());
    }
}
