package com.issuestation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateDto {
    private String pname; //입력받는것들을 DTO에 넣는거야
    private boolean isprivate;
    private String description;
    private String thumbnaillink;
}
