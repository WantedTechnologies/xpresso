package com.wantedtech.common.xpresso.types;

import java.sql.SQLException;


public interface HappySQL extends Iterable<tuple>, AutoCloseable{
	
	public void run(String query) throws SQLException;
	
	public void run(String query, Object... params) throws SQLException;
	
}
