package com.example.atipera_demo.service;


import com.example.atipera_demo.dto.BranchResponse;
import com.example.atipera_demo.dto.RepositoryResponse;
import com.example.atipera_demo.dto.github.BranchInfo;
import com.example.atipera_demo.dto.github.RepositoryInfo;
import com.example.atipera_demo.exception.UnexpectedException;
import com.example.atipera_demo.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log
public class GithubApiService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<RepositoryResponse> getRepos(String username) throws UserNotFoundException, UnexpectedException {
        String requestUrl = String.format("%s/users/%s/repos", GITHUB_API_URL, username);
        try {
            ResponseEntity<RepositoryInfo[]> response =
                    restTemplate.getForEntity(requestUrl, RepositoryInfo[].class);
            assert response.getBody() != null;
            return filterForksAndMapToResponse(response.getBody());
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 404) {
                throw new UserNotFoundException();
            }
            throw new UnexpectedException();
        }
    }

    private List<BranchInfo> fetchBranchesForRepository(RepositoryInfo repository) {
        String requestUrl = String.format("%s/repos/%s/%s/branches",
                GITHUB_API_URL, repository.getOwner().getLogin(), repository.getName());
        ResponseEntity<BranchInfo[]> response = restTemplate.getForEntity(requestUrl,
                BranchInfo[].class);
        assert response.getBody() != null;
        return Stream.of(response.getBody()).toList();
    }

    private List<RepositoryResponse> filterForksAndMapToResponse(RepositoryInfo[] infos) {
        return Stream.of(infos)
                .filter(x -> x.getFork().equals(false))
                .map(this::handleOneRepository)
                .toList();
    }

    private RepositoryResponse handleOneRepository(RepositoryInfo info) {
        List<BranchResponse> branchResponses = fetchBranchesForRepository(info)
                .stream()
                .map(this::mapBranchInfoToResponse)
                .toList();
        return RepositoryResponse.builder()
                .repositoryName(info.getName())
                .ownerLogin(info.getOwner().getLogin())
                .branches(branchResponses)
                .build();
    }

    private BranchResponse mapBranchInfoToResponse(BranchInfo branchInfo) {
        return BranchResponse.builder()
                .name(branchInfo.getName())
                .lastCommitSha(branchInfo.getCommit().getSha())
                .build();
    }
}
