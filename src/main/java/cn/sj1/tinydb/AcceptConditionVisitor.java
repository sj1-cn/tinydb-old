package cn.sj1.tinydb;

public interface AcceptConditionVisitor {
	void accept(SQLConditionVisitor visitor);
}
