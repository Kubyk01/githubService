package com.example;

import com.example.model.Branch;
import com.example.model.Commit;
import com.example.model.Owner;
import com.example.model.RepositoryResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.*;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubResourceTest {

    @Test
    public void testGetRepositories() {
        String username = "33";

        // Make the GET request
        Response response = given()
                .when().get("/github/" + username + "/repos")
                .then()
                .statusCode(200)
                .extract().response();

        String responseString = response.asString();

        RepositoryResponse repoResponse = parseResponse(responseString);

        assertNotNull(repoResponse);
        assertEquals( "33.github.io", repoResponse.name());
        assertEquals( "33", repoResponse.owner().login());
        assertEquals( "master", repoResponse.branches().get(0).name());
        assertEquals( "d13f72c6e773e1b09ff3e1adf6e90b638e24a40a", repoResponse.branches().get(0).commit().sha());
    }

    private RepositoryResponse parseResponse(String response) {
        // Regular expressions to extract data
        Pattern repoPattern = Pattern.compile("name=([^,]+)");
        Pattern ownerPattern = Pattern.compile("owner=Owner\\[login=([^\\]]+)\\]");
        Pattern branchPattern = Pattern.compile("branches=\\[Branch\\[name=([^,]+), commit=Commit\\[sha=([^\\]]+)\\]");

        Matcher repoMatcher = repoPattern.matcher(response);
        Matcher ownerMatcher = ownerPattern.matcher(response);
        Matcher branchMatcher = branchPattern.matcher(response);

        String repoName = null;
        Owner owner = null;
        List<Branch> branches = new ArrayList<>();

        if (repoMatcher.find()) {
            repoName = repoMatcher.group(1);
        }

        if (ownerMatcher.find()) {
            owner = new Owner(ownerMatcher.group(1));
        }

        while (branchMatcher.find()) {
            String branchName = branchMatcher.group(1);
            String commitSha = branchMatcher.group(2);

            Commit commit = new Commit(commitSha);
            Branch branch = new Branch(branchName, commit);
            branches.add(branch);
        }

        return new RepositoryResponse(repoName, owner, branches);
    }
}
