package cn.sj1.tinydb;

public enum OrderByOp {
	ASC("%s ASC"), DESC("%s DESC");
	final String format;

	OrderByOp(String format) {
		this.format = format;
	}
}