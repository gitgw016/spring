package net.koreate.mvc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/root-context.xml"})
public class AutoCommitTest {

	@Inject
	DataSource ds;
	
	@Test
	public void test() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "UPDATE tbl_user SET upoint = upoint + 10 WHERE uid = 'ojm'";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO tbl_message(targetid,sender,message) VALUES('bae','min','gu')";
			stmt.getConnection().createStatement();
			stmt.executeUpdate(sql);
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
