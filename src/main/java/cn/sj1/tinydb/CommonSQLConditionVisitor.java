package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommonSQLConditionVisitor implements SQLConditionVisitor {
	private StringBuilder sb;

	public CommonSQLConditionVisitor() {
		sb = new StringBuilder();
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	@Override
	public String valueOf(String value) {
		return "\'" + String.valueOf(value) + "\'";
	}

	@Override
	public String valueOf(int value) {
		return String.valueOf(value);
	}

	@Override
	public String valueOf(long value) {
		return String.valueOf(value);
	}

	@Override
	public String valueOf(Time value) {
		return String.valueOf(value);
	}

	@Override
	public String valueOf(Date value) {
		return String.valueOf(value);
	}

	@Override
	public String valueOf(Timestamp value) {
		return String.valueOf(value);
	}

	@Override
	public void visitCondition(Condition left, ConditionOp op, Condition right) {
		if (!left.isSimple()) sb.append("(");
		left.accept(this);
		if (!left.isSimple()) sb.append(")");

		sb.append(" ");
		sb.append(op);
		sb.append(" ");

		if (!right.isSimple()) sb.append("(");
		right.accept(this);
		if (!right.isSimple()) sb.append(")");
	}

	@Override
	public void visitCondition(String name, ConditionOp op) {
		sb.append(String.format(op.format, name));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, String value) {
		sb.append(String.format(op.format, name, valueOf(value)));
	}

	@Override
	public void visitLikeCondition(String name, ConditionOp op, String value) {
		sb.append(String.format(op.format, name, value));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, int value) {
		sb.append(String.format(op.format, name, valueOf(value)));

	}

	@Override
	public void visitCondition(String name, ConditionOp op, long value) {
		sb.append(String.format(op.format, name, valueOf(value)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Date value) {
		sb.append(String.format(op.format, name, valueOf(value)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Time value) {
		sb.append(String.format(op.format, name, valueOf(value)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Timestamp value) {
		sb.append(String.format(op.format, name, valueOf(value)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, String... values) {
		List<String> strValues = new ArrayList<>();
		for (String value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, int... values) {
		List<String> strValues = new ArrayList<>();
		for (int value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, long... values) {
		List<String> strValues = new ArrayList<>();
		for (long value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, Date... values) {
		List<String> strValues = new ArrayList<>();
		for (Date value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, Time... values) {
		List<String> strValues = new ArrayList<>();
		for (Time value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitComplexCondition(String name, ConditionOp op, Timestamp... values) {
		List<String> strValues = new ArrayList<>();
		for (Timestamp value : values) {
			strValues.add(valueOf(value));
		}
		sb.append(String.format(op.format, name, String.join(", ", strValues)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, String first, String second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, int first, int second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, long first, long second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Date first, Date second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Time first, Time second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

	@Override
	public void visitCondition(String name, ConditionOp op, Timestamp first, Timestamp second) {
		sb.append(String.format(op.format, name, valueOf(first), valueOf(second)));
	}

}
