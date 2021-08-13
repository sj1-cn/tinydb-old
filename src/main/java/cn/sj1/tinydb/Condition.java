package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public abstract class Condition implements AcceptConditionVisitor {

	boolean simple = true;

	public boolean isSimple() {
		return simple;
	}

	static public Condition empty() {
		return new EmptyConditionExpression();
	}

	static public ConditionBuilder<Condition> field(String name) {
		return new ConditionBuilderImpl(name);
	}

	public ConditionBuilder<Condition> and(String name) {
		if (this instanceof EmptyConditionExpression) return new ConditionBuilderImpl(name);
		else
			return new ConditionBuilderWithLeft(this, ConditionOp.AND, name);
	}

	public ConditionBuilder<Condition> or(String name) {
		if (this instanceof EmptyConditionExpression) return new ConditionBuilderImpl(name);
		else
			return new ConditionBuilderWithLeft(this, ConditionOp.OR, name);
	}

	public Condition and(Condition right) {
		if (this instanceof EmptyConditionExpression) return right;
		return new LogicalConditionExpression(this, ConditionOp.AND, right);
	}

	public Condition or(Condition right) {
		if (this instanceof EmptyConditionExpression) return right;
		return new LogicalConditionExpression(this, ConditionOp.OR, right);
	}

	static public Condition and(Condition left, Condition right) {
		return new LogicalConditionExpression(left, ConditionOp.AND, right);
	}

	static public Condition or(Condition left, Condition right) {
		return new LogicalConditionExpression(left, ConditionOp.OR, right);
	}
}

abstract class ConditionCompare extends Condition {
	final String name;
	final ConditionOp condition;

	ConditionCompare(String name, ConditionOp condition) {
		super();
		this.name = name;
		this.condition = condition;
	}
}

class EmptyConditionExpression extends Condition {

	public EmptyConditionExpression() {
		super.simple = false;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
	}

}

class LogicalConditionExpression extends Condition {
	final Condition left;
	final Condition right;
	final ConditionOp op;

	public LogicalConditionExpression(Condition left, ConditionOp op, Condition right) {
		this.left = left;
		this.right = right;
		this.op = op;
		super.simple = false;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(left, op, right);
	}

}

class SingleConditionExpression extends ConditionCompare {
	public SingleConditionExpression(String name, ConditionOp condition) {
		super(name, condition);
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition);
	}
}

class StringConditionExpression extends ConditionCompare {
	final String value;

	public StringConditionExpression(String name, ConditionOp condition, String value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		if (condition == ConditionOp.LIKE || condition == ConditionOp.STARTWITH || condition == ConditionOp.ENDWITH
				|| condition == ConditionOp.CONTAIN)
			visitor.visitLikeCondition(name, condition, value);
		else
			visitor.visitCondition(name, condition, value);
	}
}

class IntConditionExpression extends ConditionCompare {
	final int value;

	public IntConditionExpression(String name, ConditionOp condition, int value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, value);
	}
}

class LongConditionExpression extends ConditionCompare {
	final long value;

	public LongConditionExpression(String name, ConditionOp condition, long value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, value);
	}
}

class DateConditionExpression extends ConditionCompare {
	final Date value;

	public DateConditionExpression(String name, ConditionOp condition, Date value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, value);
	}
}

class TimeConditionExpression extends ConditionCompare {
	final Time value;

	public TimeConditionExpression(String name, ConditionOp condition, Time value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, value);
	}
}

class TimestampConditionExpression extends ConditionCompare {
	final Timestamp value;

	public TimestampConditionExpression(String name, ConditionOp condition, Timestamp value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, value);
	}
}

class StringBinaryConditionExpression extends ConditionCompare {
	final String first;
	final String second;

	public StringBinaryConditionExpression(String name, ConditionOp condition, String first, String second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class IntBinaryConditionExpression extends ConditionCompare {
	final int first;
	final int second;

	public IntBinaryConditionExpression(String name, ConditionOp condition, int first, int second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class LongBinaryConditionExpression extends ConditionCompare {
	final long first;
	final long second;

	public LongBinaryConditionExpression(String name, ConditionOp condition, long first, long second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class TimeBinaryConditionExpression extends ConditionCompare {
	final Time first;
	final Time second;

	public TimeBinaryConditionExpression(String name, ConditionOp condition, Time first, Time second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class DateBinaryConditionExpression extends ConditionCompare {
	final Date first;
	final Date second;

	public DateBinaryConditionExpression(String name, ConditionOp condition, Date first, Date second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class TimestampBinaryConditionExpression extends ConditionCompare {
	final Timestamp first;
	final Timestamp second;

	public TimestampBinaryConditionExpression(String name, ConditionOp condition, Timestamp first, Timestamp second) {
		super(name, condition);
		this.first = first;
		this.second = second;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitCondition(name, condition, first, second);
	}
}

class StringComplexConditionExpression extends ConditionCompare {
	final String[] value;

	public StringComplexConditionExpression(String name, ConditionOp condition, String... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}

class IntComplexConditionExpression extends ConditionCompare {
	final int[] value;

	public IntComplexConditionExpression(String name, ConditionOp condition, int... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}

class LongComplexConditionExpression extends ConditionCompare {
	final long[] value;

	public LongComplexConditionExpression(String name, ConditionOp condition, long... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}

class DateComplexConditionExpression extends ConditionCompare {
	final Date[] value;

	public DateComplexConditionExpression(String name, ConditionOp condition, Date... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}

class TimeComplexConditionExpression extends ConditionCompare {
	final Time[] value;

	public TimeComplexConditionExpression(String name, ConditionOp condition, Time... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}

class TimestampComplexConditionExpression extends ConditionCompare {
	final Timestamp[] value;

	public TimestampComplexConditionExpression(String name, ConditionOp condition, Timestamp... value) {
		super(name, condition);
		this.value = value;
	}

	@Override
	public void accept(SQLConditionVisitor visitor) {
		visitor.visitComplexCondition(name, condition, value);
	}
}
