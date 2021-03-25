package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.service.TrelloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(1);

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test_list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }

    @Test
    void shouldCreateNewCard() {

        // Given
        Trello trello = new Trello(10,23);
        TrelloAttachmentsByType trelloAttachmentsByType = new TrelloAttachmentsByType(trello);
        TrelloBadgesDto trelloBadgesDto = new TrelloBadgesDto(1,trelloAttachmentsByType);
        TrelloCard mappedTrelloCard =
                new TrelloCard("1", "test_list", "Center","5");
        TrelloCardDto trelloCard =
               new TrelloCardDto("1", "test","Center","7");
        CreatedTrelloCardDto createdTrelloCardDto =
                new CreatedTrelloCardDto("1","new card","someUrl",trelloBadgesDto);

        // When
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(trelloCard);
        when(trelloMapper.mapToCard(trelloCard)).thenReturn(mappedTrelloCard);
        when(trelloService.createTrelloCard(trelloCard)).thenReturn(createdTrelloCardDto);

        CreatedTrelloCardDto resultCard = trelloFacade.createCard(trelloCard);

        //Then
        verify(trelloValidator,times(1)).validateCard(mappedTrelloCard);
        assertThat(resultCard.getName()).isEqualTo("new card");
        assertThat(resultCard).isNotNull();
        assertThat(resultCard.getId()).isEqualTo("1");
        assertThat(resultCard.getShortUrl()).isEqualTo("someUrl");
        assertThat(resultCard.getBadges().getVotes()).isEqualTo(1);
        assertThat(resultCard.getBadges().getAttachmentsByType().getTrello().getCard()).isEqualTo(23);
        assertThat(resultCard.getBadges().getAttachmentsByType().getTrello().getBoard()).isEqualTo(10);
    }

}