package com.example.collabapp.utils;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteSearchRequest {

    private String query;

    private int page = 0;
    private int size = 10;

    @Pattern(regexp = "createdAt|updatedAt|title",message = "Sort field must be createdAt, updatedAt or title")
    private String sortBy = "createdAt";

    @Pattern(regexp = "asc|desc", message = "Sort direction must be asc or desc")
    private String sortDir = "desc";

}
