package pt.amado.wisetax;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.web.servlet.MockMvc;
import pt.amado.wisetax.base.TestProvider;
import pt.amado.wisetax.dto.ChargingRequestDTO;

@IfProfileValue(name = "skipE2ETests", value = "false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class E2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Performing the request and retrieving the expected report")
    public void shouldPerformRequestToTheTargetEndpointAndRetrieveTheReport() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ChargingRequestDTO dto = TestProvider.generateDummyChargingRequest();
        final String expectedPostOutput = "{\"chargingResult\":{\"status\":\"OK\",\"reason\":\"Eligible\"},\"gsu\":2}";

        mockMvc.perform(post("/api/v1/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedPostOutput)));

        final String expectedReportGetContentOutput = "\"tariff\":\"ALPHA_1\",\"chargingRequest\":{\"phoneNumber\":\"+1234567890\",\"createdAt\":\"2023-10-26T13:53:03.001Z\",\"service\":\"A\",\"rsu\":2,\"roaming\":true}";
        mockMvc.perform(get("/api/v1/report")
                .param("phoneNumber", dto.getPhoneNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedReportGetContentOutput)));;
    }

    @Test
    @DisplayName("Should return an error when an invalid phoneNumber is provided")
    public void shouldFailWhenIsProvidedAnInvalidPhoneArgument() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ChargingRequestDTO dto = TestProvider.generateDummyChargingRequest();
        dto.setPhoneNumber("a999999999");
        final String expectedPostOutput = "An error occurred while validation of provided input";

        mockMvc.perform(post("/api/v1/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedPostOutput)));
    }

    @Test
    @DisplayName("Should return an error when an invalid service is provided")
    public void shouldFailWhenIsProvidedAnInvalidServiceArgument() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ChargingRequestDTO dto = TestProvider.generateDummyChargingRequest();
        dto.setService("C");
        final String expectedPostOutput = "An error occurred while validation of provided input";

        mockMvc.perform(post("/api/v1/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(expectedPostOutput)));
    }
}
