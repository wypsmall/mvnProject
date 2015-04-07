package com.neo.test.seq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Sequence {
	private static final Log log = LogFactory.getLog(Sequence.class);
	private int blockSize;
	private long startValue;
	private static final String GET_SQL = "select id from sequence_value where name = ?";
	private static final String NEW_SQL = "insert into sequence_value (id,name) values (?,?)";
	private static final String UPDATE_SQL = "update sequence_value set id = ?  where name = ? and id = ?";
	private Map<String, Step> stepMap;
	private DataSource dataSource;

	public Sequence() {
		this.blockSize = 5;
		this.startValue = 0L;

		this.stepMap = new HashMap();
	}

	private boolean getNextBlock(String sequenceName, Step step) {
		Long value = getPersistenceValue(sequenceName);
		if (value == null) {
			try {
				value = newPersistenceValue(sequenceName);
			} catch (Exception e) {
				log.error("newPersistenceValue error!");
				value = getPersistenceValue(sequenceName);
			}
		}
		boolean b = saveValue(value.longValue(), sequenceName) == 1;
		if (b) {
			step.setCurrentValue(value.longValue());
			step.setEndValue(value.longValue() + this.blockSize);
		}
		return b;
	}

	public synchronized long get(String sequenceName) {
		Step step = (Step) this.stepMap.get(sequenceName);
		if (step == null) {
			step = new Step(this.startValue, this.startValue + this.blockSize);
			this.stepMap.put(sequenceName, step);
		} else if (step.currentValue < step.endValue) {
			return step.incrementAndGet();
		}

		for (int i = 0; i < this.blockSize; i++) {
			if (getNextBlock(sequenceName, step)) {
				return step.incrementAndGet();
			}
		}
		throw new RuntimeException("No more value.");
	}

	private int saveValue(long value, String sequenceName) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement("update sequence_value set id = ?  where name = ? and id = ?");
			statement.setLong(1, value + this.blockSize);
			statement.setString(2, sequenceName);
			statement.setLong(3, value);
			return statement.executeUpdate();
		} catch (Exception e) {
			log.error("newPersistenceValue error!", e);
			throw new RuntimeException("newPersistenceValue error!", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("close connection error!", e);
				}
		}
	}

	private Long getPersistenceValue(String sequenceName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement("select id from sequence_value where name = ?");
			statement.setString(1, sequenceName);
			resultSet = statement.executeQuery();
			if (resultSet.next())
				return Long.valueOf(resultSet.getLong("id"));
		} catch (Exception e) {
			log.error("getPersistenceValue error!", e);
			throw new RuntimeException("getPersistenceValue error!", e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					log.error("close resultset error!", e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("close connection error!", e);
				}
			}
		}
		return null;
	}

	private Long newPersistenceValue(String sequenceName) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement("insert into sequence_value (id,name) values (?,?)");
			statement.setLong(1, this.startValue);
			statement.setString(2, sequenceName);
			statement.executeUpdate();
		} catch (Exception e) {
			log.error("newPersistenceValue error!", e);
			throw new RuntimeException("newPersistenceValue error!", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					log.error("close statement error!", e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("close connection error!", e);
				}
			}
		}
		return Long.valueOf(this.startValue);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public void setStartValue(long startValue) {
		this.startValue = startValue;
	}

	static class Step {
		private long currentValue;
		private long endValue;

		Step(long currentValue, long endValue) {
			this.currentValue = currentValue;
			this.endValue = endValue;
		}

		public void setCurrentValue(long currentValue) {
			this.currentValue = currentValue;
		}

		public void setEndValue(long endValue) {
			this.endValue = endValue;
		}

		public long incrementAndGet() {
			return ++this.currentValue;
		}
	}
}
