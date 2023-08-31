# atipera_recruitment

This is recruitment task for Atipera. 

## How to run it
`mvn clean spring-boot:run`

## Endpoint
GET `/api/repos/{username}`

After calling this endpoint with header "Accept: application/json" and valid username you will get object like this with 200 response code:

```json
{
    "repositories": [{
        "repositoryName": "repositoryName",
        "ownerLogin": "ownerLogin",
        "branches": [{
            "name": "branchName",
            "lastCommitSha": "shaOfLastCommit"
        }]
    }]
}
```

If you call this endpoint with header "Accept: application/xml" you will get object like this with 406 response code:

```json
{
    "status": 406,
    "message": "Header 'Accept' has xml value"
}
```

If you call this endpoint with username that doesn't exist you will get this response with 404 response code:

```json
{
  "status": 404,
  "message": "User was not found"
}
```

If error will occur while calling Github API you will get this response with 418 error code:
```json
{
  "status": 418,
  "message": "Unexpected exception"
}
```
