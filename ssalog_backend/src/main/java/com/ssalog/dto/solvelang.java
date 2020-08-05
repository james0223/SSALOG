package com.ssalog.dto;

public class solvelang {
	private long time_sum;
	private long memory_sum;
	private long cnt;
	public solvelang() {}
	public solvelang(long time_sum, long memory_sum, long cnt) {
		super();
		this.time_sum = time_sum;
		this.memory_sum = memory_sum;
		this.cnt = cnt;
	}
	public long getTime_sum() {
		return time_sum;
	}
	public void setTime_sum(long time_sum) {
		this.time_sum = time_sum;
	}
	public long getMemory_sum() {
		return memory_sum;
	}
	public void setMemory_sum(long memory_sum) {
		this.memory_sum = memory_sum;
	}
	public long getCnt() {
		return cnt;
	}
	public void setCnt(long cnt) {
		this.cnt = cnt;
	}
	
}
