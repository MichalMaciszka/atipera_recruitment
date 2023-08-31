package com.example.atipera_demo.controller;


import com.example.atipera_demo.dto.FinalResponse;
import com.example.atipera_demo.dto.RepositoryResponse;
import com.example.atipera_demo.exception.UnexpectedException;
import com.example.atipera_demo.exception.UserNotFoundException;
import com.example.atipera_demo.exception.XmlHeaderException;
import com.example.atipera_demo.service.GithubApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class DemoController {
    private static final String ACCEPT_HEADER_XML_VALUE = "application/xml";
    private static final String ACCEPT_HEADER_JSON_VALUE = "application/json";

    private final GithubApiService service;

    @GetMapping("/repos/{username}")
    public FinalResponse getRepos(
            @PathVariable String username,
            @RequestHeader(HttpHeaders.ACCEPT) String accept) throws XmlHeaderException,
            UserNotFoundException, UnexpectedException {
        if (accept.equals(ACCEPT_HEADER_XML_VALUE)) {
            throw new XmlHeaderException();
        } else if (!accept.equals(ACCEPT_HEADER_JSON_VALUE)) {
            throw new UnexpectedException();
        }
        List<RepositoryResponse> result = service.getRepos(username);
        return new FinalResponse(result);
    }
}
