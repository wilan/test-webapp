package test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import test.modules.SqlModule;
import test.modules.TestServletModule;

public class TestServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new TestServletModule(), new SqlModule());
	}

}
