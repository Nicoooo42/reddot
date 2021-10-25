package com.reddot.core.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Thread.class)
public abstract class Thread_ {

	public static volatile SingularAttribute<Thread, String> name;
	public static volatile SingularAttribute<Thread, Long> id;
	public static volatile SetAttribute<Thread, Post> posts;
	public static volatile SingularAttribute<Thread, Board> board;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String POSTS = "posts";
	public static final String BOARD = "board";

}

