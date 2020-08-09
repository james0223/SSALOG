package com.ssalog.dto;

// 잔디용 맵퍼
public class jandi {
	private String _id;
	private long count;
	public jandi() {}
	public jandi(String _id, long count) {
		super();
		this._id = _id;
		this.count = count;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
}
