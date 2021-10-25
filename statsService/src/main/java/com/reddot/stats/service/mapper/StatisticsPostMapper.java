package com.reddot.stats.service.mapper;

import com.reddot.stats.domain.StatisticsPost;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatisticsPost} and its DTO {@link StatisticsPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatisticsPostMapper extends EntityMapper<StatisticsPostDTO, StatisticsPost> {}
