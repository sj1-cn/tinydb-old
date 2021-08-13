package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class ConditionBuilderWithLeft extends ConditionBuilderImpl {
	final Condition left;
	final ConditionOp op;

	public ConditionBuilderWithLeft(Condition left, ConditionOp op, String lastName) {
		super(lastName);
		this.left = left;
		this.op = op;
	}

	@Override
	public Condition condition(ConditionOp type) {
		Condition right = super.condition(type);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, String value) {
		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, int value) {

		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, long value) {

		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, Date value) {

		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, Time value) {

		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition condition(ConditionOp type, Timestamp value) {

		Condition right = super.condition(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, String... value) {

		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, int... value) {

		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, long... value) {

		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, Date... value) {

		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, Time... value) {
		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

	@Override
	public Condition conditionComplex(ConditionOp type, Timestamp... value) {

		Condition right = super.conditionComplex(type, value);
		return new LogicalConditionExpression(left, op, right);
	}

}
