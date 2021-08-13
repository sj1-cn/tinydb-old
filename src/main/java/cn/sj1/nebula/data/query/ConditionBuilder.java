package cn.sj1.nebula.data.query;

import static cn.sj1.nebula.data.query.ConditionOp.CONTAIN;
import static cn.sj1.nebula.data.query.ConditionOp.ENDWITH;
import static cn.sj1.nebula.data.query.ConditionOp.EQ;
import static cn.sj1.nebula.data.query.ConditionOp.GE;
import static cn.sj1.nebula.data.query.ConditionOp.GT;
import static cn.sj1.nebula.data.query.ConditionOp.IN;
import static cn.sj1.nebula.data.query.ConditionOp.ISNOTNULL;
import static cn.sj1.nebula.data.query.ConditionOp.ISNULL;
import static cn.sj1.nebula.data.query.ConditionOp.LE;
import static cn.sj1.nebula.data.query.ConditionOp.LIKE;
import static cn.sj1.nebula.data.query.ConditionOp.LT;
import static cn.sj1.nebula.data.query.ConditionOp.NE;
import static cn.sj1.nebula.data.query.ConditionOp.NOTLIKE;
import static cn.sj1.nebula.data.query.ConditionOp.NotIn;
import static cn.sj1.nebula.data.query.ConditionOp.STARTWITH;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public interface ConditionBuilder<T> {
	// @formatter:off
	default T eq(String value) {return condition(EQ, value);}
	default T eq(int value) {return condition(EQ, value);}
	default T eq(long value) {return condition(EQ, value);}
	default T eq(Date value) {return condition(EQ, value);}
	default T eq(Time value) {return condition(EQ, value);}
	default T eq(Timestamp value) {return condition(EQ, value);}
	
	default T ne(String value) {return condition(NE, value);}
	default T ne(int value) {return condition(NE, value);}
	default T ne(long value) {return condition(NE, value);}
	default T ne(Date value) {return condition(NE, value);}
	default T ne(Time value) {return condition(NE, value);}
	default T ne(Timestamp value) {return condition(NE, value);}
	

	default T gt(String value) {return condition(GT, value);}
	default T gt(int value) {return condition(GT, value);}
	default T gt(long value) {return condition(GT, value);}
	default T gt(Date value) {return condition(GT, value);}
	default T gt(Time value) {return condition(GT, value);}
	default T gt(Timestamp value) {return condition(GT, value);}
	

	default T ge(String value) {return condition(GE, value);}
	default T ge(int value) {return condition(GE, value);}
	default T ge(long value) {return condition(GE, value);}
	default T ge(Date value) {return condition(GE, value);}
	default T ge(Time value) {return condition(GE, value);}
	default T ge(Timestamp value) {return condition(GE, value);}
	

	default T lt(String value) {return condition(LT, value);}
	default T lt(int value) {return condition(LT, value);}
	default T lt(long value) {return condition(LT, value);}
	default T lt(Date value) {return condition(LT, value);}
	default T lt(Time value) {return condition(LT, value);}
	default T lt(Timestamp value) {return condition(LT, value);}
	

	default T le(String value) {return condition(LE, value);}
	default T le(int value) {return condition(LE, value);}
	default T le(long value) {return condition(LE, value);}
	default T le(Date value) {return condition(LE, value);}
	default T le(Time value) {return condition(LE, value);}
	default T le(Timestamp value) {return condition(LE, value);}

	default T isNull() {return condition(ISNULL);}		
	default T isNotNull() {return condition(ISNOTNULL);}	

	default T startWith(String value) {return condition(STARTWITH, value);}
	default T endWith(String value) {return condition(ENDWITH, value);}
	default T contain(String value) {return condition(CONTAIN, value);}
	default T like(String value) {return condition(LIKE, value);}	
	default T notLike(String value) {return condition(NOTLIKE, value);}	
	
	T between(String first, String second);
	T between(int first, int second);
	T between(long first, long second) ;
	T between(Date first, Date second);
	T between(Time first, Time second);
	T between(Timestamp first, Timestamp second) ;

	default T in(String... values) {return conditionComplex(IN, values);}
	default T in(int... values) {return conditionComplex(IN, values);}
	default T in(long... values) {return conditionComplex(IN, values);}
	default T in(Date... values) {return conditionComplex(IN, values);}
	default T in(Time... values) {return conditionComplex(IN, values);}
	default T in(Timestamp... values) {return conditionComplex(IN, values);}

	default T notIn(String... values) {return conditionComplex(NotIn, values);}
	default T notIn(int... values) {return conditionComplex(NotIn, values);}
	default T notIn(long... values) {return conditionComplex(NotIn, values);}
	default T notIn(Date... values) {return conditionComplex(NotIn, values);}
	default T notIn(Time... values) {return conditionComplex(NotIn, values);}
	default T notIn(Timestamp... values) {return conditionComplex(NotIn, values);}

	T condition(ConditionOp type);
	
	T condition(ConditionOp type,String value);
	T condition(ConditionOp type,int value);
	T condition(ConditionOp type,long value);
	T condition(ConditionOp type,Date value);
	T condition(ConditionOp type,Time value);
	T condition(ConditionOp type,Timestamp value);
	
	T conditionComplex(ConditionOp type,String... value);
	T conditionComplex(ConditionOp type,int... value);
	T conditionComplex(ConditionOp type,long... value);
	T conditionComplex(ConditionOp type,Date... value);
	T conditionComplex(ConditionOp type,Time... value);
	T conditionComplex(ConditionOp type,Timestamp... value);
}
