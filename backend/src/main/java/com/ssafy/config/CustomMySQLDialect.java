package com.ssafy.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect extends MySQL57Dialect {
	public CustomMySQLDialect() {
		super();
		
		registerFunction(
		    "group_concat",
		    new StandardSQLFunction(
		        "group_concat",
		        StandardBasicTypes.STRING
		    )
		);
	}
}
