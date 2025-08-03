package com.example.graphqlserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Map;

@GraphQlTest(BookController.class)
public class BookControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstBook() {
        this.graphQlTester
				.documentName("bookDetails")
				.variable("id", "book-1")
                .execute()
                .path("bookById")
                .matchesJson("""
                    {
                        "id": "book-1",
                        "name": "Effective Java",
                        "pageCount": 416,
                        "author": {
                          "firstName": "Joshua",
                          "lastName": "Bloch"
                        }
                    }
                """);
    }

    @Test
    void shouldAddNewBook() {
        this.graphQlTester
                .documentName("addBook")
                .variables(Map.of(
                        "name", "GraphQL Basics",
                        "pageCount", 250,
                        "authorId", "42"
                ))
                .execute()
                .path("addBook")
                .matchesJson("""
                    {
                        "id": "book-4",
                        "name": "GraphQL Basics",
                        "pageCount": 250,
                        "author": null
                    }
                """);

        this.graphQlTester
                .documentName("bookDetails")
                .variable("id", "book-4")
                .execute()
                .path("bookById")
                .matchesJson("""
                    {
                        "id": "book-4",
                        "name": "GraphQL Basics",
                        "pageCount": 250,
                        "author": null
                    }
                """);
    }
}
