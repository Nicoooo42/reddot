package com.reddot.core.service.mapper;

import com.reddot.core.domain.Board;
import com.reddot.core.service.dto.BoardDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-26T02:19:34+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Eclipse Foundation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toEntity(BoardDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Board board = new Board();

        board.setId( dto.getId() );
        board.setName( dto.getName() );

        return board;
    }

    @Override
    public BoardDTO toDto(Board entity) {
        if ( entity == null ) {
            return null;
        }

        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId( entity.getId() );
        boardDTO.setName( entity.getName() );

        return boardDTO;
    }

    @Override
    public List<Board> toEntity(List<BoardDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Board> list = new ArrayList<Board>( dtoList.size() );
        for ( BoardDTO boardDTO : dtoList ) {
            list.add( toEntity( boardDTO ) );
        }

        return list;
    }

    @Override
    public List<BoardDTO> toDto(List<Board> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BoardDTO> list = new ArrayList<BoardDTO>( entityList.size() );
        for ( Board board : entityList ) {
            list.add( toDto( board ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Board entity, BoardDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }

    @Override
    public BoardDTO toDtoId(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId( board.getId() );

        return boardDTO;
    }
}
