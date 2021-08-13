package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

class WhereBuilder implements ConditionBuilder<WhereBuilder> {

	public static WhereBuilder empty() {
		return new WhereBuilder();
	}

	@Override
	public String toString() {
		CommonSQLConditionVisitor conditionVisitorImpl = new CommonSQLConditionVisitor();
		lastCondition.accept(conditionVisitorImpl);
		return conditionVisitorImpl.toString();
	}

	ConditionBuilderImpl lastConditionBuilder = null;
	Condition lastCondition = null;

	public ConditionBuilder<WhereBuilder> name(String name) {
		lastConditionBuilder = new ConditionBuilderImpl(name);
		return this;
	}

	public ConditionBuilder<WhereBuilder> and(String name) {
		lastConditionBuilder = new ConditionBuilderImpl(name);
		return this;
	}

	public WhereBuilder and(Condition right) {
		lastCondition = new LogicalConditionExpression(lastCondition, ConditionOp.AND, right);
		return this;
	}

	public WhereBuilder or(Condition right) {
		lastCondition = new LogicalConditionExpression(lastCondition, ConditionOp.OR, right);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type) {
		lastCondition = lastConditionBuilder.condition(type);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, String value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, int value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, long value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, Date value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, Time value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder condition(ConditionOp type, Timestamp value) {
		lastCondition = lastConditionBuilder.condition(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, String... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, int... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, long... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, Date... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, Time... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder conditionComplex(ConditionOp type, Timestamp... value) {
		lastCondition = lastConditionBuilder.conditionComplex(type, value);
		return this;
	}

	@Override
	public WhereBuilder between(String first, String second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

	@Override
	public WhereBuilder between(int first, int second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

	@Override
	public WhereBuilder between(long first, long second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

	@Override
	public WhereBuilder between(Date first, Date second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

	@Override
	public WhereBuilder between(Time first, Time second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

	@Override
	public WhereBuilder between(Timestamp first, Timestamp second) {
		lastCondition = lastConditionBuilder.between(first, second);
		return this;
	}

}
