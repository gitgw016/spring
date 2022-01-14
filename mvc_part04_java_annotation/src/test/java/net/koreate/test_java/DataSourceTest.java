package net.koreate.test_java;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.koreate.test_java.config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
public class DataSourceTest {

	@Inject
	DataSource ds;
	
	@Test
	public void test() {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			System.out.println("dataSource 등록됨 "+conn);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("실패 ^^");
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
