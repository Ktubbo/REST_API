package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void mapToBoards() {

        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1","To Do", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2","In Progress", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3","Done", true);
        List<TrelloListDto> listDtos1 = new ArrayList<>();
        List<TrelloListDto> listDtos2 = new ArrayList<>();
        listDtos1.add(trelloListDto1);
        listDtos1.add(trelloListDto2);
        listDtos2.add(trelloListDto3);
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1","New Board",listDtos1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2","Test Board 2",listDtos2);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(trelloBoard.get(0).getId(),"1");
        assertEquals(trelloBoard.get(1).getName(),"Test Board 2");
        assertEquals(trelloBoard.get(0).getLists().get(1).getName(), "In Progress");
        assertTrue(trelloBoard.get(1).getLists().get(0).isClosed());
    }

    @Test
    void mapToBoardsDto() {

        //Given
        TrelloList trelloList1 = new TrelloList("1","To Do",false);
        TrelloList trelloList2 = new TrelloList("2","In Progress", false);
        TrelloList trelloList3 = new TrelloList("3","Done", true);
        List<TrelloList> trelloLists1 = new ArrayList<>();
        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloLists1.add(trelloList1);
        trelloLists1.add(trelloList2);
        trelloLists2.add(trelloList3);
        TrelloBoard trelloBoard1 = new TrelloBoard("1","New Board", trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Test Board 2", trelloLists2);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals(trelloBoardDtoList.get(1).getId(),"2");
        assertEquals(trelloBoardDtoList.get(0).getLists().get(1).getName(),"In Progress");
        assertTrue(trelloBoardDtoList.get(1).getLists().get(0).isClosed());
    }

    @Test
    void mapToList() {

        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1","To Do", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("2","In Progress", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("3","Done", true);
        List<TrelloListDto> listDtos = new ArrayList<>();
        listDtos.add(trelloListDto1);
        listDtos.add(trelloListDto2);
        listDtos.add(trelloListDto3);

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(listDtos);

        //Then
        assertEquals(trelloLists.get(0).getId(),"1");
        assertEquals(trelloLists.get(1).getName(),"In Progress");
        assertTrue(trelloLists.get(2).isClosed());
    }

    @Test
    void mapToListDto() {

        //Given
        TrelloList trelloList1 = new TrelloList("1","To Do",false);
        TrelloList trelloList2 = new TrelloList("2","In Progress", false);
        TrelloList trelloList3 = new TrelloList("3","Done", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);
        trelloLists.add(trelloList3);

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(trelloListDtos.get(0).getId(),"1");
        assertEquals(trelloListDtos.get(1).getName(), "In Progress");
        assertTrue(trelloListDtos.get(2).isClosed());
    }

    @Test
    void mapToCardDto() {

        //Given
        TrelloCard trelloCard = new TrelloCard("new Trello Card","some new Card","Center","7");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCardDto.getName(),"new Trello Card");
        assertEquals(trelloCardDto.getIdList(),"7");
    }

    @Test
    void mapToCard() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("new Trello Dto Card", "some new Dto Card", "Right","9");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCard.getListId(),"9");
        assertEquals(trelloCard.getDescription(),"some new Dto Card");
    }
}