package com.neu.jbuddy.basic;

public class BuddyException extends RuntimeException {
	/** serialVersionUID */
    private static final long serialVersionUID = 8891233704371075154L;

    public BuddyException(Throwable e) {
		super(e);
		errorCode = -9999;
		errorMsg = e.getMessage();
	}

	public BuddyException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BuddyException(String errorMsg) {
		this.errorCode = -9999;
		this.errorMsg = errorMsg;
	}

	public BuddyException(int errorCode) {
		this.errorCode = errorCode;
		this.errorMsg = "unknow error!";
	}

	String errorMsg = null;

	int errorCode = 0;
}
