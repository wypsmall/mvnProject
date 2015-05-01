package com.neo.parse;

import java.util.Arrays;

import com.alibaba.cobar.CobarConfig;
import com.alibaba.cobar.config.model.SchemaConfig;
import com.alibaba.cobar.manager.parser.ManagerParse;
import com.alibaba.cobar.manager.parser.ManagerParseSelect;
import com.alibaba.cobar.parser.ast.stmt.SQLStatement;
import com.alibaba.cobar.parser.recognizer.SQLParserDelegate;
import com.alibaba.cobar.parser.recognizer.mysql.syntax.MySQLParser;
import com.alibaba.cobar.route.RouteResultset;
import com.alibaba.cobar.route.RouteResultsetNode;
import com.alibaba.cobar.route.ServerRouter;
import com.alibaba.cobar.route.visitor.PartitionKeyVisitor;

public class TestMysqlParse {
	public static void main(String[] args) throws Exception {
		try {
			String sql = "insert into tb2 (id, name) values (511,'fda')";
			int rs = ManagerParse.parse(sql);
			System.out.println(rs & 0xff);
			rs = ManagerParseSelect.parse(sql, rs >>> 8);
			System.out.println(rs);
			
			String db = "dbtest";
			CobarConfig config = new CobarConfig();
			SchemaConfig schema = config.getSchemas().get(db);
			if (schema == null) {
				System.out.println("Unknown database '" + db + "'");
				return;
			}
			// 路由计算
			RouteResultset rrs = ServerRouter.route(schema, sql, "utf8", "frontCon");
			RouteResultsetNode[] res = rrs.getNodes();
			for (RouteResultsetNode routeResultsetNode : res) {
				String stmt = routeResultsetNode.getStatement();
				System.out.println(routeResultsetNode.getName() + "  " + stmt);
				
				SQLStatement ast = SQLParserDelegate.parse(stmt, MySQLParser.DEFAULT_CHARSET);
				PartitionKeyVisitor visitor = new PartitionKeyVisitor(schema.getTables());
				ast.accept(visitor);
				//需要继续分析sql解析原理
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
