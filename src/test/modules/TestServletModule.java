package test.modules;

import com.google.inject.servlet.ServletModule;

import test.servlets.TradeServlet;

public class TestServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		serve("/trade").with(TradeServlet.class);
	}
}
