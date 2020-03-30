package com.megazone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UiController {

    private static final Logger logger = LoggerFactory.getLogger(UiController.class);

    @Value("${api.zuuluri}")
    private String zuulUri;

    @Value("${api.eurekauri}")
    private String eurekaUri;

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = {"/configRefresh"}, method = RequestMethod.GET)
    @ResponseBody
    public Map configRefresh(HttpServletRequest httpServletRequest) {
        Map map = new HashMap();
        RestTemplate rest = new RestTemplate();
        rest.postForEntity("http://localhost:8080/refresh",null,null);
        return map;
    }

    @RequestMapping(value = {"/zuultest"}, method = RequestMethod.GET)
    @ResponseBody
    public Map zuultest(HttpServletRequest httpServletRequest) {
        RestTemplate zuulRest = new RestTemplate();
        //ZULL
        String zuulUrl = zuulUri + "/users";
        logger.info(zuulUrl);
        ResponseEntity res = zuulRest.getForEntity(zuulUrl, String.class);
        Map ress = new HashMap();
        ress.put("result", res);
        logger.info(res.toString());
        return ress;
    }


    @RequestMapping(value = {"/eurekatest"}, method = RequestMethod.GET)
    @ResponseBody
    public Map eurekatest(HttpServletRequest httpServletRequest) {
        //EUREKA
        String eurekaUrl = eurekaUri + "/users";
        logger.info(eurekaUri);
        ResponseEntity res = restTemplate.getForEntity(eurekaUrl, String.class);
        Map ress = new HashMap();
        ress.put("result", res);
        logger.info(res.toString());
        return ress;
    }


    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest httpServletRequest, ModelAndView model) {
        model.setViewName("index");
        return model;
    }


}
