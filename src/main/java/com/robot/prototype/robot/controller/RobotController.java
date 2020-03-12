package com.robot.prototype.robot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.robot.prototype.robot.model.ResponseModel;
import com.robot.prototype.robot.service.IRobotService;

@RestController
public class RobotController {

	@Autowired
	IRobotService iRobotService;

	@GetMapping("/robot")
	public ResponseModel get(@RequestParam(name = "batteryLevel") double batteryLevel,
			@RequestParam(name = "loadWeight") double loadWeight, @RequestParam(name = "distance") double distance) {
		return iRobotService.getRobotStatus(batteryLevel, loadWeight, distance);
	}
	
	@GetMapping("/readBarcode")
	public ResponseModel readBarcode() {
		return iRobotService.getPrice();
	}

}


