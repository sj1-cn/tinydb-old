package cn.sj1.tinydb;

public interface OrderByBuilder {
	OrderByBuilder asc();
	OrderByBuilder desc();
	OrderByBuilder field(String name);
}
