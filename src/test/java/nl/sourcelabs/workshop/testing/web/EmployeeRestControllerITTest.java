package nl.sourcelabs.workshop.testing.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class EmployeeRestControllerITTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void checkNotNull() {
        assertThat(mockMvc).isNotNull();
    }

}