package cn.sj1.nebula.data.query;

public interface AcceptConditionVisitor {
	void accept(SQLConditionVisitor visitor);
}
