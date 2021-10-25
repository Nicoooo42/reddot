package com.reddot.core.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Board.class)
public abstract class Board_ {

	public static volatile SingularAttribute<Board, String> name;
	public static volatile SetAttribute<Board, Thread> threads;
	public static volatile SingularAttribute<Board, Long> id;

	public static final String NAME = "name";
	public static final String THREADS = "threads";
	public static final String ID = "id";

}

