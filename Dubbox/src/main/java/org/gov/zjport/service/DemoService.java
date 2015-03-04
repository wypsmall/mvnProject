package org.gov.zjport.service;

import com.alibaba.dubbo.demo.user.User;


public interface DemoService {
	public void sayHello();
	public User getUser(Long id);
}
