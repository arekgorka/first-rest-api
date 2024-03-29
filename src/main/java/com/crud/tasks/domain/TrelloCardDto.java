package com.crud.tasks.domain;

import lombok.*;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrelloCardDto {

    private String name;
    private String description;
    private String pos;
    private String listId;
}
