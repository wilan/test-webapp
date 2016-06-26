package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.appengine.labs.repackaged.com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mysql.jdbc.Statement;

public class TradeDao {
	private final Provider<Connection> connProvider;

	@Inject
	public TradeDao(Provider<Connection> connProvider) {
		this.connProvider = connProvider;
	}
	
	public ImmutableList<Trade> getAllTrades() throws SQLException {
		try (Connection conn = connProvider.get()) {
			ResultSet rs = conn.prepareStatement("SELECT * FROM Trade").executeQuery();
			ImmutableList.Builder<Trade> tradeBuilder = ImmutableList.builder();
			while (rs.next()) {
				tradeBuilder.add(new Trade(rs.getInt(1), rs.getString(2)));
			}
			return tradeBuilder.build();
		}
	}
	
	public Trade getTrade(int id) throws SQLException {
		try (Connection conn = connProvider.get()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Trade WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Trade(rs.getInt(1), rs.getString(2));
			}
			throw new RuntimeException("Does not exist");
		}
	}
	
	public void deleteTrade(int id) throws SQLException {
		try (Connection conn = connProvider.get()) {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Trade WHERE id = ?");
			ps.setInt(1, id);
			ps.execute();
		}
	}
	
	public Trade createTrade(String value) throws SQLException {
		try (Connection conn = connProvider.get()) {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Trade (value) values(?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, value);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
			  return getTrade(rs.getInt(1));
			}
			throw new RuntimeException("Failed to create.");
		}
	}
	
	public Trade updateTrade(int id, String newValue) throws SQLException {
		try (Connection conn = connProvider.get()) {
			PreparedStatement ps = conn.prepareStatement("UPDATE Trade SET value = ? WHERE id = ?");
			ps.setString(1, newValue);
			ps.setInt(2, id);
			ps.execute();
			return getTrade(id);
		}
	}
}
