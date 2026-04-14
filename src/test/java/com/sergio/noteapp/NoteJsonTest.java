package com.sergio.noteapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class NoteJsonTest {

    @Autowired
    private JacksonTester<Note> json;

    @Test
    void noteSerializationTest() throws IOException {
        Note note = new Note(20L, "Note 20", "Content 20", "sergio");
        var jsonContent = json.write(note); // JsonContent<Note>
        assertThat(jsonContent).isStrictlyEqualToJson("expected.json");
        assertThat(jsonContent)
                .hasJsonPathNumberValue("@.id")
                .extractingJsonPathNumberValue("@.id").isEqualTo(20);
        assertThat(jsonContent)
                .hasJsonPathStringValue("@.title")
                .extractingJsonPathStringValue("@.title").isEqualTo("Note 20");
        assertThat(jsonContent)
                .hasJsonPathStringValue("@.content")
                .extractingJsonPathStringValue("@.content").isEqualTo("Content 20");
        assertThat(jsonContent)
                .hasJsonPathStringValue("@.owner")
                .extractingJsonPathStringValue("@.owner").isEqualTo("sergio");
    }

    @Test
    void noteDeserializationTest() throws IOException {
        String expected = """
        {
            "id": 20,
            "title": "Note 20",
            "content": "Content 20",
            "owner": "sergio"
        }
        """;
        Note note = json.parseObject(expected);
        assertThat(note).isEqualTo(new Note(20L, "Note 20", "Content 20", "sergio"));
        assertThat(note.id()).isEqualTo(20L);
        assertThat(note.title()).isEqualTo("Note 20");
        assertThat(note.content()).isEqualTo("Content 20");
        assertThat(note.owner()).isEqualTo("sergio");
    }
}
