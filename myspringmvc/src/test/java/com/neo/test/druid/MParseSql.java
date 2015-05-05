package com.neo.test.druid;

import java.util.Map;
import java.util.Set;

import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.stat.TableStat.Name;

public class MParseSql {

	public static void main(String[] args) {
		String sql = "select o.*,t.* from ordertable o left join trans t on t.orderid=o.orderid";
		MySqlStatementParser parser = new MySqlStatementParser(sql);
		SQLStatement statement = parser.parseStatement();
		SchemaStatVisitor visitor = new SchemaStatVisitor();
		statement.accept(visitor);
		Map<Name, TableStat> tables = visitor.getTables();
		Set<Name> names = tables.keySet();
		for (Name name : names) {
			System.out.println(name.getName());
		}
		Set<Column> cols = visitor.getColumns();
		for (Column column : cols) {
			System.out.println(column.getName());
		}
		
		SQLSelectStatement selectStatement = (SQLSelectStatement)statement;
		selectStatement.getSelect().setOrderBy(new SQLOrderBy(new SQLIdentifierExpr("o.orderid desc")));
		System.out.println(selectStatement.toString());
	}

}
