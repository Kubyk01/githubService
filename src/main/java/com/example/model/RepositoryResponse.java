package com.example.model;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RepositoryResponse(
        @JsonProperty("name") String name,
        @JsonProperty("owner") Owner owner,
        @JsonProperty("branches") List<Branch> branches
) {

}
