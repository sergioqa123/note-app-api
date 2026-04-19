package com.sergio.noteapp;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteappApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

	@Test
    void shouldReturnANoteWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/notes/20", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(20);
        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Note 20");
        String content = documentContext.read("$.content");
        assertThat(content).isEqualTo("Content 20");
        String owner = documentContext.read("$.owner");
        assertThat(owner).isEqualTo("sergio");
    }

    @Test
    void shouldNotReturnANoteWhenIdIsUnknown() {
        ResponseEntity<String> response = restTemplate.getForEntity("/notes/2000", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldCreateANewNote() {
        Note note = new Note(null, "Note 77", "Content 77", "sergio");
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("/notes", note, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewNote = responseEntity.getHeaders().getLocation();
        ResponseEntity<String> response = restTemplate.getForEntity(locationOfNewNote, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
        String title = documentContext.read("$.title");
        assertThat(title).isEqualTo("Note 77");
        String content = documentContext.read("$.content");
        assertThat(content).isEqualTo("Content 77");
        String owner = documentContext.read("$.owner");
        assertThat(owner).isEqualTo("sergio");
    }
}
