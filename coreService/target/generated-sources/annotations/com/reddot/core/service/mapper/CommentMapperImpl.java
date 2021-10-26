package com.reddot.core.service.mapper;

import com.reddot.core.domain.Comment;
import com.reddot.core.service.dto.CommentDTO;
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
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private PostMapper postMapper;

    @Override
    public Comment toEntity(CommentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( dto.getId() );
        comment.setName( dto.getName() );
        comment.setContent( dto.getContent() );
        comment.post( postMapper.toEntity( dto.getPost() ) );
        comment.comment( toEntity( dto.getComment() ) );

        return comment;
    }

    @Override
    public List<Comment> toEntity(List<CommentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( dtoList.size() );
        for ( CommentDTO commentDTO : dtoList ) {
            list.add( toEntity( commentDTO ) );
        }

        return list;
    }

    @Override
    public List<CommentDTO> toDto(List<Comment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( entityList.size() );
        for ( Comment comment : entityList ) {
            list.add( toDto( comment ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Comment entity, CommentDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getPost() != null ) {
            entity.post( postMapper.toEntity( dto.getPost() ) );
        }
        if ( dto.getComment() != null ) {
            entity.comment( toEntity( dto.getComment() ) );
        }
    }

    @Override
    public CommentDTO toDto(Comment s) {
        if ( s == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setPost( postMapper.toDtoId( s.getPost() ) );
        commentDTO.setComment( toDtoId( s.getComment() ) );
        commentDTO.setId( s.getId() );
        commentDTO.setName( s.getName() );
        commentDTO.setContent( s.getContent() );

        return commentDTO;
    }

    @Override
    public CommentDTO toDtoId(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );

        return commentDTO;
    }
}
