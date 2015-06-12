package com.wantedtech.common.xpresso.types;

import java.sql.SQLException;


public interface HappySQL extends Iterable<tuple>, AutoCloseable{
	
	public HappySQL execute(String query) throws SQLException;
	
	public HappySQL execute(String query, Object... params) throws SQLException;
	
}
