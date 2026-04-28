package com.logistics.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.item.application.port.ItemRepositoryPort;
import com.logistics.item.domain.Item;
import com.logistics.item.infrastructure.rest.dto.request.PatchItemRequestDTO;
import com.logistics.item.infrastructure.rest.dto.request.PostItemRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@Slf4j
class ItemApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepositoryPort itemRepository;

    @BeforeEach
    public void beforeEach() {
        log.info("Deleting items in database");
        itemRepository.deleteAll();

        List<Item> data = generateTestItems(20);
        itemRepository.saveAll(data);
    }

    @Test
    void getAllItems_shouldReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllItems_shouldReturn204NoContent() throws Exception {
        itemRepository.deleteAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getItemById_shouldReturn404IfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/items/123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getItemById_shouldReturn200IfFound() throws Exception {
        String id = itemRepository.getItems().getFirst().getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/items/".concat(id)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void createItem_shouldReturn201AndLocationHeader() throws Exception {
        PostItemRequestDTO dto = PostItemRequestDTO.builder()
                .name("Test item")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.matchesRegex(".*/items/.*")));
    }

    @Test
    void createItem_shouldReturn422IfMissingFields() throws Exception {
        PostItemRequestDTO dto = PostItemRequestDTO.builder()
                .build(); // falta name

        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void createItem_shouldReturn409IfDuplicated() throws Exception {
        String existingItemName = itemRepository.getItems().getFirst().getName();

        PostItemRequestDTO dto = PostItemRequestDTO.builder()
                .name(existingItemName)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void deleteItem_shouldReturn204IfSuccessful() throws Exception {
        String id = itemRepository.getItems().stream().findFirst().get().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/".concat(id)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteItem_shouldReturn404IfNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/".concat(UUID.randomUUID().toString())))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteItem_shouldReturn422IfInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/invalid"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void patchItem_shouldReturn204IfSuccessful() throws Exception {
        String id = itemRepository.getItems().stream().findFirst().get().getId();
        PatchItemRequestDTO dto = PatchItemRequestDTO.builder()
                .name("New name")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/items/".concat(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void patchItem_shouldReturn404IfNotFound() throws Exception {
        PatchItemRequestDTO dto = PatchItemRequestDTO.builder()
                .name("asdf")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/items/".concat(UUID.randomUUID().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void patchItem_shouldReturn422IfInvalidInput() throws Exception {
        String id = itemRepository.getItems().stream().findFirst().get().getId();
        PatchItemRequestDTO dto = PatchItemRequestDTO.builder()
                .name("")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/items/".concat(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    private static List<Item> generateTestItems(int size){
      return IntStream.range(0, size).boxed()
              .map(i -> {
                  String id = UUID.randomUUID().toString();
                  return Item.builder().id(id).name("TEST-ITEM-" + id).build();
              })
              .toList();
    }
}