package com.reddot.core.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ {

	public static volatile SetAttribute<Post, Comment> comments;
	public static volatile SingularAttribute<Post, String> name;
	public static volatile SingularAttribute<Post, Long> id;
	public static volatile SingularAttribute<Post, Thread> thread;
	public static volatile SingularAttribute<Post, String> userName;
	public static volatile SingularAttribute<Post, String> content;

	public static final String COMMENTS = "comments";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String THREAD = "thread";
	public static final String USER_NAME = "userName";
	public static final String CONTENT = "content";

}

