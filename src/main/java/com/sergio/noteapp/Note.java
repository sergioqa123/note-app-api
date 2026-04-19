package com.sergio.noteapp;

import org.springframework.data.annotation.Id;

public record Note(@Id Long id, String title, String content, String owner) {
}
