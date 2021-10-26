package com.reddot.core.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.reddot.core.domain.Comment;
import com.reddot.core.service.dto.CommentDTO;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { PostMapper.class })
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "post", source = "post", qualifiedByName = "id")
    @Mapping(target = "comment", source = "comment", qualifiedByName = "id")
    CommentDTO toDto(Comment s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommentDTO toDtoId(Comment comment);
}
