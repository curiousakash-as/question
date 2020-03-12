package com.robot.prototype.robot.service;

import com.robot.prototype.robot.model.ResponseModel;

public interface IRobotService {
	public Boolean checkBatteryLevel(double levelPercent);

	public Boolean checkLoadWeight(double loadWeight);

	public ResponseModel getRobotStatus(double batteryLevel, double loadWeight, double distance);
	// public Double getRemainingCharge();

	public ResponseModel getPrice();
}