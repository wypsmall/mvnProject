package org.gov.zjport.service.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gov.zjport.service.DemoService;

import com.alibaba.dubbo.demo.user.User;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

@Path("Demo")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class DemoServiceImpl implements DemoService{

	@GET
    @Path("test")
	public void sayHello() {
		System.out.println("测试输出：");
	}

	@GET
    @Path("user/{id : \\d+}")
	public User getUser(@PathParam("id") Long id) {
		return new User(id, "user-"+id);
	}

}
