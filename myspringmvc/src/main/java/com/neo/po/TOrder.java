/**
 * <p>Copyright: Copyright(c) 2015</p>
 * <p>Company: gome.com.cn</p>
 * <p>2015年3月3日上午10:22:19</p>
 * @author wangyunpeng
 * @version 1.0
 */
package com.neo.po;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * desc:
 * <p>创建人：wangyunpeng 创建日期：2015年3月3日上午10:22:19</p>
 * @version V1.0  
 */
/**
CREATE TABLE `order` (
  `orderid` varchar(32) NOT NULL,
  `orderdate` varchar(10) NOT NULL DEFAULT '',
  `amount` int(11) NOT NULL DEFAULT '0',
  `userid` varchar(32) NOT NULL DEFAULT '',
  `createdt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class TOrder implements Serializable {

	private static final long serialVersionUID = -227981223430077373L;
	
	private String orderId;
	private String orderDate;
	private Integer amount;
	private String userId;
	private Timestamp createDt;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Timestamp getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}


}
