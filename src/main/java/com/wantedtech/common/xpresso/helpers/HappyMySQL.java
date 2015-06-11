package com.wantedtech.common.xpresso.helpers;

import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple0;

public class HappyMySQL implements HappySQL{

	String dbClass = "com.mysql.jdbc.Driver";
	String dbHost;
	String userName;
	String username;
	String password;
	String dbName;
	
	Connection connection;
	
	ResultSet resultSet;
	
	public HappyMySQL(String dbHost, String userName, String password, String dbName) throws ClassNotFoundException, SQLException{
		this.dbHost = dbHost;
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
		Class.forName(dbClass);
		connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, userName, password);
	}
	
	public void run(String query) throws SQLException{
		Statement statement = connection.createStatement();
		resultSet = statement.executeQuery(query);
	}
	
	public void run(String query, Object... params) throws SQLException{
		PreparedStatement statement = connection.prepareStatement(query);
		for (tuple item : x.enumerate(params,1)){
			item.name("idx", "param");
			if (item.get("param") instanceof String){
				statement.setString(item.get("idx", Integer.class), item.get("param", String.class));	
			}else{
				statement.setObject(item.get("idx", Integer.class), item.get("param"));
			}
		}
		if(x.String(query).toLowerCase().startsWith("select ")){
			resultSet = statement.executeQuery();	
		}else{
			statement.executeUpdate();
		}
	}	

	@Override
	public Iterator<tuple> iterator() {
		return new Iterator<tuple>(){

			@Override
			public boolean hasNext() {
				try {
					return resultSet.next();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}

			@Override
			public tuple next() {
				
				ResultSetMetaData metaData;
				
				try {
					
					metaData = resultSet.getMetaData();
					
					int count;
					try {
						count = metaData.getColumnCount(); //number of column
						String columnName[] = new String[count];

						for (int i = 1; i <= count; i++) {
						   columnName[i-1] = metaData.getColumnLabel(i); 
						}
						tuple row;
						switch (columnName.length) {
							case 1:
								row = x.tuple(resultSet.getObject(1));
								row.name(columnName[0]);
								break;
							default:
								row = x.tuple(resultSet.getObject(1),resultSet.getObject(2));
								row.name(columnName[0],columnName[1]);
								break;
						}
						return row;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return tuple0.valueOf();
			}
		};
	}

	@Override
	public void close() throws Exception {
		connection.close();

	}
}
