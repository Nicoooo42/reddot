package com.reddot.stats.service.mapper;

import com.reddot.stats.domain.StatisticsPost;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-26T01:27:41+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Eclipse Foundation)"
)
@Component
public class StatisticsPostMapperImpl implements StatisticsPostMapper {

    @Override
    public StatisticsPost toEntity(StatisticsPostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StatisticsPost statisticsPost = new StatisticsPost();

        statisticsPost.setId( dto.getId() );
        statisticsPost.setUser( dto.getUser() );
        statisticsPost.setYear( dto.getYear() );
        statisticsPost.setMonth( dto.getMonth() );
        statisticsPost.setDay( dto.getDay() );

        return statisticsPost;
    }

    @Override
    public StatisticsPostDTO toDto(StatisticsPost entity) {
        if ( entity == null ) {
            return null;
        }

        StatisticsPostDTO statisticsPostDTO = new StatisticsPostDTO();

        statisticsPostDTO.setId( entity.getId() );
        statisticsPostDTO.setUser( entity.getUser() );
        statisticsPostDTO.setYear( entity.getYear() );
        statisticsPostDTO.setMonth( entity.getMonth() );
        statisticsPostDTO.setDay( entity.getDay() );

        return statisticsPostDTO;
    }

    @Override
    public List<StatisticsPost> toEntity(List<StatisticsPostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StatisticsPost> list = new ArrayList<StatisticsPost>( dtoList.size() );
        for ( StatisticsPostDTO statisticsPostDTO : dtoList ) {
            list.add( toEntity( statisticsPostDTO ) );
        }

        return list;
    }

    @Override
    public List<StatisticsPostDTO> toDto(List<StatisticsPost> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StatisticsPostDTO> list = new ArrayList<StatisticsPostDTO>( entityList.size() );
        for ( StatisticsPost statisticsPost : entityList ) {
            list.add( toDto( statisticsPost ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(StatisticsPost entity, StatisticsPostDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getUser() != null ) {
            entity.setUser( dto.getUser() );
        }
        if ( dto.getYear() != null ) {
            entity.setYear( dto.getYear() );
        }
        if ( dto.getMonth() != null ) {
            entity.setMonth( dto.getMonth() );
        }
        if ( dto.getDay() != null ) {
            entity.setDay( dto.getDay() );
        }
    }
}
