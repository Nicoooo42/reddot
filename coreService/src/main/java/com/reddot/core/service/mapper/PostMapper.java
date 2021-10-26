package com.reddot.core.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.reddot.core.domain.Post;
import com.reddot.core.service.dto.PostDTO;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = { ThreadMapper.class })
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "thread", source = "thread", qualifiedByName = "id")
    PostDTO toDto(Post s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoId(Post post);
}
