package cn.sj1.nebula.data.query;

public interface OrderByBuilder {
	OrderByBuilder asc();
	OrderByBuilder desc();
	OrderByBuilder field(String name);
}
