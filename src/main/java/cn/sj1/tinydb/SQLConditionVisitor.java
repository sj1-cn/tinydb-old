package cn.sj1.tinydb;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

interface SQLConditionVisitor {
	void visitCondition(Condition left, ConditionOp op, Condition right);

	void visitCondition(String name, ConditionOp op);

	void visitLikeCondition(String name, ConditionOp op, String value);
	
	void visitCondition(String name, ConditionOp op, String value);

	void visitCondition(String name, ConditionOp op, int value);

	void visitCondition(String name, ConditionOp op, long value);

	void visitCondition(String name, ConditionOp op, Date value);

	void visitCondition(String name, ConditionOp op, Time value);

	void visitCondition(String name, ConditionOp op, Timestamp value);

	void visitCondition(String name, ConditionOp op, String first, String second);

	void visitCondition(String name, ConditionOp op, int first, int second);

	void visitCondition(String name, ConditionOp op, long first, long second);

	void visitCondition(String name, ConditionOp op, Date first, Date second);

	void visitCondition(String name, ConditionOp op, Time first, Time second);

	void visitCondition(String name, ConditionOp op, Timestamp first, Timestamp second);
	
	void visitComplexCondition(String name, ConditionOp op, String... value);

	void visitComplexCondition(String name, ConditionOp op, int... value);

	void visitComplexCondition(String name, ConditionOp op, long... value);

	void visitComplexCondition(String name, ConditionOp op, Date... value);

	void visitComplexCondition(String name, ConditionOp op, Time... value);

	void visitComplexCondition(String name, ConditionOp op, Timestamp... value);

	String valueOf(String value);

	String valueOf(int value);

	String valueOf(long value);

	String valueOf(Time value);

	String valueOf(Date value);

	String valueOf(Timestamp value);
}
