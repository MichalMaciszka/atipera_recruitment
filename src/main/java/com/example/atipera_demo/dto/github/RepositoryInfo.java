package com.example.atipera_demo.dto.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RepositoryInfo {
    private String name;
    private Boolean fork;
    private OwnerInfo owner;
}
