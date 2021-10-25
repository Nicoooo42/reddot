package com.reddot.core.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, Post> post;
	public static volatile SingularAttribute<Comment, String> name;
	public static volatile SingularAttribute<Comment, Comment> comment;
	public static volatile SingularAttribute<Comment, Long> id;
	public static volatile SingularAttribute<Comment, String> content;

	public static final String POST = "post";
	public static final String NAME = "name";
	public static final String COMMENT = "comment";
	public static final String ID = "id";
	public static final String CONTENT = "content";

}

