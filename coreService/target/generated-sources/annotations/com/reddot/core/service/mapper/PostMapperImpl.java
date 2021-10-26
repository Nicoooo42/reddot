package com.reddot.core.service.mapper;

import com.reddot.core.domain.Post;
import com.reddot.core.service.dto.PostDTO;
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
public class PostMapperImpl implements PostMapper {

    @Autowired
    private ThreadMapper threadMapper;

    @Override
    public Post toEntity(PostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( dto.getId() );
        post.setName( dto.getName() );
        post.setUserName( dto.getUserName() );
        post.setContent( dto.getContent() );
        post.thread( threadMapper.toEntity( dto.getThread() ) );

        return post;
    }

    @Override
    public List<Post> toEntity(List<PostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( dtoList.size() );
        for ( PostDTO postDTO : dtoList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }

    @Override
    public List<PostDTO> toDto(List<Post> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( entityList.size() );
        for ( Post post : entityList ) {
            list.add( toDto( post ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Post entity, PostDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getUserName() != null ) {
            entity.setUserName( dto.getUserName() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getThread() != null ) {
            entity.thread( threadMapper.toEntity( dto.getThread() ) );
        }
    }

    @Override
    public PostDTO toDto(Post s) {
        if ( s == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setThread( threadMapper.toDtoId( s.getThread() ) );
        postDTO.setId( s.getId() );
        postDTO.setName( s.getName() );
        postDTO.setContent( s.getContent() );
        postDTO.setUserName( s.getUserName() );

        return postDTO;
    }

    @Override
    public PostDTO toDtoId(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setId( post.getId() );

        return postDTO;
    }
}
