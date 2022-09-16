package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("1","name",true);
        trelloListDtoList.add(trelloListDto);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("name", "1",trelloListDtoList);
        trelloBoardDtoList.add(trelloBoardDto);
        //When
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardDtoList);
        //Then
        assertEquals(trelloBoard.get(0).getName(),trelloBoardDto.getName());
        assertEquals(trelloBoard.get(0).getId(),trelloBoardDto.getId());
        assertEquals(trelloBoard.get(0).getLists().get(0).getId(),trelloBoardDto.getLists().get(0).getId());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> trelloListList = new ArrayList<>();
        TrelloList trelloList = new TrelloList("1","name",true);
        trelloListList.add(trelloList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "name",trelloListList);
        trelloBoardList.add(trelloBoard);
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        //Then
        assertEquals(trelloBoardDtoList.get(0).getName(),trelloBoard.getName());
        assertEquals(trelloBoardDtoList.get(0).getId(),trelloBoard.getId());
        assertEquals(trelloBoardDtoList.get(0).getLists().get(0).getId(),trelloBoard.getLists().get(0).getId());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("1", "name", true);
        trelloListDtoList.add(trelloListDto);
        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDtoList);
        //Then
        assertEquals("1",trelloList.get(0).getId());
        assertEquals("name", trelloList.get(0).getName());
        assertTrue(trelloList.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloListList = new ArrayList<>();
        TrelloList trelloList = new TrelloList("1","name",true);
        trelloListList.add(trelloList);
        //When
        List<TrelloListDto> trelloListDtoList = trelloMapper.mapToListDto(trelloListList);
        //Then
        assertEquals("1",trelloListDtoList.get(0).getId());
        assertEquals("name", trelloListDtoList.get(0).getName());
        assertTrue(trelloListDtoList.get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name","desc","1","1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("name",trelloCardDto.getName());
        assertEquals("desc",trelloCardDto.getDescription());
        assertEquals("1",trelloCardDto.getPos());
        assertEquals("1",trelloCardDto.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","desc","1","1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("name",trelloCard.getName());
        assertEquals("desc",trelloCard.getDescription());
        assertEquals("1",trelloCard.getPos());
        assertEquals("1",trelloCard.getListId());
    }
}
