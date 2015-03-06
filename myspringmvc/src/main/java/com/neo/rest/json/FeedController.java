//FINAL 
package com.neo.rest.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
