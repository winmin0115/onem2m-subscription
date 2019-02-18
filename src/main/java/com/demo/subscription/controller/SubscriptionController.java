package com.demo.subscription.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.subscription.util.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@Controller
public class SubscriptionController {
	//
	private static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
	@RequestMapping(value = "/{deviceId}/{containerId}")
	public void subscriptionData(@PathVariable String deviceId, @PathVariable String containerId, @RequestBody String body) {
		//
		logger.debug("[deviceId : "+ deviceId + ", containerId : "+ containerId +"] , Body : [" + body + "]");
		try {
			this.subscriptionParser(body);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//
	/**
	 *  플랫폼에서 전달 받은 JSON을 파싱
	 * @param body
	 * @return
	 */
	public void subscriptionParser(String body) throws Exception {
		logger.debug("[subscriptionParsing] body = [ " + body + "]");
		JsonObject subscriptionObject = JsonUtil.fromJson(body, JsonObject.class);
		JsonObject nev = subscriptionObject.getAsJsonObject("m2m:sgn")
						.getAsJsonObject("nev");
		JsonObject rep = nev.getAsJsonObject("rep");
		JsonObject om = nev.getAsJsonObject("om");
	
		if(om.get("op").toString().equals("1")) { // Create 된 데이터만 사용함 [op가 1일때 생성]
			try {
				JsonObject content = JsonUtil.fromJson(rep.getAsJsonObject("m2m:cin").get("con").getAsString(), JsonObject.class);
				logger.debug("resultContents : [ " + JsonUtil.toJson(content) + "]");
			}catch (JsonSyntaxException e) {
				logger.error("JsonSyntaxException : " + rep.getAsJsonObject("m2m:cin").get("con").getAsString());
			}
					
			
		}
						
	}
		
				
						
	

}
