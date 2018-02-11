package org.bgalusha.email.sparkmail.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
		public T mapRow(ResultSet rs) throws SQLException;
}
