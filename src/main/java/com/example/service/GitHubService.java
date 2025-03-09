package com.example.service;

import com.example.client.GitHubClient;
import com.example.model.RepositoryResponse;
import com.example.model.Branch;
import com.example.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GitHubService {

    @RestClient
    GitHubClient gitHubClient;

    public Uni<List<?>> getUserRepositories(String username) {
        return gitHubClient.getRepositories(username)
                .onItem().transformToUni(repos -> {
                    if (repos.isEmpty()) {
                        throw new NotFoundException("User not found");
                    }

                    return Uni.combine().all().unis(
                            repos.stream()
                                    .filter(repo -> !repo.isFork())
                                    .map(repo -> gitHubClient.getBranches(repo.getOwner().login(), repo.getName())
                                            .map(branches -> new RepositoryResponse(
                                                    repo.getName(),
                                                    repo.getOwner(),
                                                    branches)))
                                    .collect(Collectors.toList())
                    ).combinedWith(List::copyOf);
                });
    }
}
