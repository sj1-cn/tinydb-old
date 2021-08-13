package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ConditionBuilderImpl implements ConditionBuilder<Condition> {
	final String lastName;

	public ConditionBuilderImpl(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Condition condition(ConditionOp type) {
		return new SingleConditionExpression(lastName, type);
	}

	@Override
	public Condition condition(ConditionOp type, String value) {
		return new StringConditionExpression(lastName, type, value);
	}

	@Override
	public Condition condition(ConditionOp type, int value) {
		return new IntConditionExpression(lastName, type, value);

	}

	@Override
	public Condition condition(ConditionOp type, long value) {
		return new LongConditionExpression(lastName, type, value);

	}

	@Override
	public Condition condition(ConditionOp type, Date value) {
		return new DateConditionExpression(lastName, type, value);

	}

	@Override
	public Condition condition(ConditionOp type, Time value) {
		return new TimeConditionExpression(lastName, type, value);

	}

	@Override
	public Condition condition(ConditionOp type, Timestamp value) {
		return new TimestampConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, String... value) {
		return new StringComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, int... value) {
		return new IntComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, long... value) {
		return new LongComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, Date... value) {
		return new DateComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, Time... value) {
		return new TimeComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition conditionComplex(ConditionOp type, Timestamp... value) {
		return new TimestampComplexConditionExpression(lastName, type, value);

	}

	@Override
	public Condition between(String first, String second) {
		return new StringBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

	@Override
	public Condition between(int first, int second) {
		return new IntBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

	@Override
	public Condition between(long first, long second) {
		return new LongBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

	@Override
	public Condition between(Date first, Date second) {
		return new DateBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

	@Override
	public Condition between(Time first, Time second) {
		return new TimeBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

	@Override
	public Condition between(Timestamp first, Timestamp second) {
		return new TimestampBinaryConditionExpression(lastName, ConditionOp.BETWEEN, first, second);
	}

}
