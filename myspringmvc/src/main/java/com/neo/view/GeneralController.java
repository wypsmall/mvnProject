package com.neo.view;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralController {

	@RequestMapping(value="index.do")
	public void index_jsp(Model model){
		model.addAttribute("liming", "黎明你好");
		System.out.println("index.jsp");
	}
	@RequestMapping(value = "/sub.do", produces = "text/plain;charset=UTF-8") 
	@ResponseBody
	public String revPageData(@RequestParam(value="name") String name
			, @RequestParam(value="email") String email) {
		System.out.println("name:" + name + ", email:" + email);
		JSONObject json = new JSONObject();
		/*
		 * "success": true,
    		"message": "Message sent successfully."
		 */
		json.put("success", true);
		json.put("message", "成功提交【 submit success!】");
		return json.toString();
	}
}
