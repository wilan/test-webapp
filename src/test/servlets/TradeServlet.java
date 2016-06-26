package test.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import test.dao.TradeDao;

@SuppressWarnings("serial")
@Singleton
public class TradeServlet extends HttpServlet {
	private final TradeDao dao;

	@Inject
	public TradeServlet(TradeDao dao) {
		this.dao = dao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
		  Gson gson = new Gson();
		  String id = req.getParameter("id");
		  // If no ID is specified return all trades.
			String json = gson.toJson(Strings.isNullOrEmpty(id) ? dao.getAllTrades() : dao.getTrade(Integer.valueOf(id)));
			resp.getWriter().print(json);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	  try {
      Gson gson = new Gson();
      JSONObject data = getDataFromReq(req);
      String json = gson.toJson(dao.updateTrade(Integer.valueOf(data.getInt("id")), data.getString("value")));
      resp.getWriter().print(json);
    } catch (SQLException | JSONException e) {
      throw new RuntimeException(e);
    }
	}
	
	@Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	  try {
      JSONObject data = getDataFromReq(req);
      Gson gson = new Gson();
      String json = gson.toJson(dao.createTrade(data.getString("value")));
      resp.getWriter().print(json);
    } catch (SQLException | JSONException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      String id = req.getParameter("id");
      dao.deleteTrade(Integer.valueOf(id));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static JSONObject getDataFromReq(HttpServletRequest request) throws IOException {
    StringBuffer jb = new StringBuffer();
    String line = null;
    try {
      BufferedReader reader = request.getReader();
      while ((line = reader.readLine()) != null)
        jb.append(line);
    } catch (Exception e) { /*report an error*/ }

    try {
      return new JSONObject(jb.toString());
    } catch (Exception e) {
      // crash and burn
      throw new IOException("Error parsing JSON request string");
    }
  }
}
