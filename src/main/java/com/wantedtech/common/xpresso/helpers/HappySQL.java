package com.wantedtech.common.xpresso.helpers;

import java.sql.SQLException;

import com.wantedtech.common.xpresso.types.tuple;

public interface HappySQL extends Iterable<tuple>, AutoCloseable{
	
	public void run(String query) throws SQLException;
	
	public void run(String query, Object... params) throws SQLException;
	
}
