package com.robot.prototype.robot.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robot.prototype.robot.exceptions.IllegalUrlException;
import com.robot.prototype.robot.model.ResponseModel;
import com.robot.prototype.robot.service.IRobotService;
import com.robot.prototype.robot.service.RobotService;

@RestController
public class RobotController{

	@Autowired
	IRobotService iRobotService;

	@GetMapping("/robot")
	public ResponseModel get(@RequestParam(name = "batteryLevel", required = false) Double batteryLevel,
			@RequestParam(name = "loadWeight", required = false) Double loadWeight, @RequestParam(name = "distance", required = false) Double distance) {
		ResponseModel robotService=null;
		
		try {
			robotService=validateInputs(batteryLevel, loadWeight, distance);
			//robotService= iRobotService.getRobotStatus(batteryLevel, loadWeight, distance);	
//			if(null != loadWeight || null != batteryLevel || null != distance)
//				robotService= iRobotService.getRobotStatus(batteryLevel, loadWeight, distance);	
//			else
//				throw new Exception();
			
		}
		catch(IllegalUrlException e) {
			robotService=iRobotService.setErrorMessage("Invalid URL");
			
			//robotService.setErrorMessage("Error occured while processing the request");
		}
		catch (Exception e) {
			robotService=iRobotService.setErrorMessage("Unknown Error Occured");
		}
		return robotService;
	//	return iRobotService.getRobotStatus(batteryLevel, loadWeight, distance);
	}
//	@GetMapping("/helloWorld")
//		public ResponseModel get(@RequestParam(name = "name", defaultValue = "akash") String name, @RequestParam(name = "locale", defaultValue = "en") String locale, Locale loc) {
//		 ResponseModel hello = new ResponseModel();
//		 if(loc.getLanguage()=="en_IN")
//		 hello.setMessage("hello : " +name);
//		 else
//			 hello.setMessage("namaste: "   + name);
//		 return hello;
//	}
	@GetMapping("/readBarcode")
	public ResponseModel readBarcode() {
		return iRobotService.getPrice();
	}
	
	private ResponseModel validateInputs(Double batteryLevel, Double loadWeight, Double distance) throws IllegalUrlException {
		ResponseModel robotService=null;
		if(null != loadWeight && null != batteryLevel && null != distance)
			robotService= iRobotService.getRobotStatus(batteryLevel, loadWeight, distance);	
		else
			throw new IllegalUrlException();
		return robotService;
	}
	
}


