package com.example.graphqlserver;

public record BookInput(String name, Integer pageCount, String authorId) {
}
