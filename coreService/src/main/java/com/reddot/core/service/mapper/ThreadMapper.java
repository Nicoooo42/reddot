package com.reddot.core.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.reddot.core.domain.Thread;
import com.reddot.core.service.dto.ThreadDTO;

/**
 * Mapper for the entity {@link Thread} and its DTO {@link ThreadDTO}.
 */
@Mapper(componentModel = "spring", uses = { BoardMapper.class })
public interface ThreadMapper extends EntityMapper<ThreadDTO, Thread> {
    @Mapping(target = "board", source = "board", qualifiedByName = "id")
    ThreadDTO toDto(Thread s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ThreadDTO toDtoId(Thread thread);
}
