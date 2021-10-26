package com.reddot.core.service.mapper;

import com.reddot.core.domain.Thread;
import com.reddot.core.service.dto.ThreadDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-26T02:19:34+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Eclipse Foundation)"
)
@Component
public class ThreadMapperImpl implements ThreadMapper {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public Thread toEntity(ThreadDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Thread thread = new Thread();

        thread.setId( dto.getId() );
        thread.setName( dto.getName() );
        thread.board( boardMapper.toEntity( dto.getBoard() ) );

        return thread;
    }

    @Override
    public List<Thread> toEntity(List<ThreadDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Thread> list = new ArrayList<Thread>( dtoList.size() );
        for ( ThreadDTO threadDTO : dtoList ) {
            list.add( toEntity( threadDTO ) );
        }

        return list;
    }

    @Override
    public List<ThreadDTO> toDto(List<Thread> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ThreadDTO> list = new ArrayList<ThreadDTO>( entityList.size() );
        for ( Thread thread : entityList ) {
            list.add( toDto( thread ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Thread entity, ThreadDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getBoard() != null ) {
            entity.board( boardMapper.toEntity( dto.getBoard() ) );
        }
    }

    @Override
    public ThreadDTO toDto(Thread s) {
        if ( s == null ) {
            return null;
        }

        ThreadDTO threadDTO = new ThreadDTO();

        threadDTO.setBoard( boardMapper.toDtoId( s.getBoard() ) );
        threadDTO.setId( s.getId() );
        threadDTO.setName( s.getName() );

        return threadDTO;
    }

    @Override
    public ThreadDTO toDtoId(Thread thread) {
        if ( thread == null ) {
            return null;
        }

        ThreadDTO threadDTO = new ThreadDTO();

        threadDTO.setId( thread.getId() );

        return threadDTO;
    }
}
