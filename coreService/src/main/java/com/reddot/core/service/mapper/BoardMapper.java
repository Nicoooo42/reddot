package com.reddot.core.service.mapper;

import com.reddot.core.domain.Board;
import com.reddot.core.service.dto.BoardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Board} and its DTO {@link BoardDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BoardMapper extends EntityMapper<BoardDTO, Board> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BoardDTO toDtoId(Board board);
}
