package org.example.api.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.example.api.dao.entity.Collection;
import org.example.api.utils.result.Result;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest
class CollectionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CollectionControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

    @Test
    void createCollection() {
        Collection collection = new Collection();
        collection.setName("test collection");
        byte[] body = assertDoesNotThrow(() -> mapper.writeValueAsBytes(collection));

        byte[] resp = assertDoesNotThrow(() -> mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/collection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsByteArray()
        );
        Result<?> result = assertDoesNotThrow(() -> mapper.readValue(resp, Result.class));
        assertTrue(result.isSuccess());
    }

    @Test
    void queryCollectionList() {
        byte[] resp = assertDoesNotThrow(() -> mockMvc.perform(MockMvcRequestBuilders.get("/collection"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray()
        );
        @SuppressWarnings("unchecked")
        Result<List<Collection>> result = assertDoesNotThrow(() -> mapper.readValue(resp, Result.class));
        assertTrue(result.isSuccess());
        assertTrue(CollectionUtils.isNotEmpty(result.getData()));
    }
}