package test.modules;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class SqlModule extends AbstractModule {
	@Override
	protected void configure() {
	}
	
	@Provides
	public Connection getConnection() {
	  String cls = null;
	  String url = null;
	  if (SystemProperty.environment.value() ==
	      SystemProperty.Environment.Value.Production)  {
	    cls = "com.mysql.jdbc.GoogleDriver";
	    url = System.getProperty("cloudsql-url");
	  } else {
	    cls = "com.mysql.jdbc.Driver";
	    url = System.getProperty("cloudsql-url-local");
	  }
		try {
		  Class.forName(cls);
			return DriverManager.getConnection(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
