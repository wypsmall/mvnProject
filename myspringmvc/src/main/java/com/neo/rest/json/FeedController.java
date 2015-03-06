//FINAL 
package com.neo.rest.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.rest.feeds.OrgTree;
import com.neo.rest.feeds.TournamentContent;


@Controller
public class FeedController {
    @RequestMapping("/jsonfeed")
    public String getJSON(Model model) {
        List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "World Cup", "www.fifa.com/worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "Confederations Cup", "www.fifa.com/confederationscup/"));
        model.addAttribute("items", tournamentList);
        model.addAttribute("status", 0);
        return "";
    }
    
    @RequestMapping("/datagrid_data1")
    public String getGridJSON(Model model) {
    	List<TournamentContent> tournamentList = new ArrayList<TournamentContent>();
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "World Cup", "www.fifa.com/worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-20 World Cup", "www.fifa.com/u20worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "U-17 World Cup", "www.fifa.com/u17worldcup/"));
        tournamentList.add(TournamentContent.generateContent("FIFA", new Date(), "Confederations Cup", "www.fifa.com/confederationscup/"));
        model.addAttribute("rows", tournamentList);
    	model.addAttribute("total", 4);
    	return "";
    }
    
    @RequestMapping("/tree_data1")
    @ResponseBody
    public String getTreeJSON(Model model) {
    	List<OrgTree> nodes = new ArrayList<OrgTree>();
    	OrgTree root = new OrgTree();
    	root.setId("1");
    	root.setText("root");
    	root.setState("1");
    	for (int i = 0; i < 3; i++) {
    		OrgTree child = new OrgTree();
    		child.setId("i");
    		child.setText("child-" + i);
    		child.setState("1");
    		root.getChildren().add(child);
		}
    	nodes.add(root);
    	JSONArray ja = JSONArray.fromObject(nodes);
    	System.out.println(ja.toString());
    	return ja.toString();
    }
}
