package com.reddot.stats.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatisticsPost.class)
public abstract class StatisticsPost_ {

	public static volatile SingularAttribute<StatisticsPost, Integer> month;
	public static volatile SingularAttribute<StatisticsPost, Integer> year;
	public static volatile SingularAttribute<StatisticsPost, Long> id;
	public static volatile SingularAttribute<StatisticsPost, String> user;
	public static volatile SingularAttribute<StatisticsPost, Integer> day;

	public static final String MONTH = "month";
	public static final String YEAR = "year";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String DAY = "day";

}

