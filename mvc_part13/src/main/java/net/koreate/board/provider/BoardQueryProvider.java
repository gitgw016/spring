package net.koreate.board.provider;

import org.apache.ibatis.jdbc.SQL;

import net.koreate.common.utils.Criteria;

public class BoardQueryProvider {

	public String searchSelectSql(Criteria cri) {
		/*
		SQL sql = new SQL();
		// SELECT (*) FROM
		sql.SELECT("*");
		sql.FROM("re_tbl_board");
		if(cri.getSearchType().equals("t")) {
		sql.WHERE("title LIKE CONCAT('%',"+cri.getKeyword()+",'%')");
		}
		sql.ORDER_BY("origin DESC, seq ASC");
		sql.LIMIT(cri.getPageStart()+","+cri.getPerPageNum());
		*/
		SQL sql = new SQL();
		sql.SELECT("B.* , U.uname AS writer");
		sql.FROM("re_tbl_board AS B NATURAL JOIN tbl_user AS U");
		/*
		sql.JOIN("tbl_user AS U");
		sql.WHERE("B.uno = U.uno");
		*/
		getSearchWhere(cri, sql);
		
		sql.ORDER_BY("B.origin DESC, B.seq ASC");
		sql.LIMIT(cri.getPageStart()+" , "+cri.getPerPageNum());
		
		String query = sql.toString();
		System.out.println(query);
		return query;
		
	}
	
	public String searchSelectCount(Criteria cri) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM("re_tbl_board");
				getSearchWhere(cri, this);
			}
		}.toString();
	}
	
	// WHERE 조건절 추가
	public void getSearchWhere(Criteria cri, SQL sql) {
		if(cri.getSearchType() != null && !cri.getSearchType().equals("n")) {
			String titleQuery = "title LIKE CONCAT('%','"+cri.getKeyword()+"','%')";
			String contentQuery = "content LIKE CONCAT('%','"+cri.getKeyword()+"','%')";
			switch(cri.getSearchType()) {
			case "t" :
				sql.WHERE(titleQuery);
				break;
			case "c" :
				sql.WHERE(contentQuery);
				break;
			case "tc" :
				sql.WHERE(titleQuery).OR().WHERE(contentQuery);
				break;
			}
		}
	}
}
