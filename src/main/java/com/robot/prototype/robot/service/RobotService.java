package com.robot.prototype.robot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.robot.prototype.robot.exceptions.IllegalUrlException;
import com.robot.prototype.robot.model.ResponseModel;

@Service
public class RobotService implements IRobotService {

	@Autowired
	private RestTemplate restTemplate;

	public Boolean checkLoadWeight(double loadWeight) {
		if (loadWeight > 10.0 || loadWeight < 0.0)
			return true;
		else
			return false;
	}

	public Boolean checkBatteryLevel(double levelPercent) {
		if (levelPercent <= 15 || levelPercent < 0.0)
			return true;
		else
			return false;
	}

	private Double remainingBatteryNoWeight(double distance, double batteryLevel) {
		// 100 -> 5
		// 1 -> 5/100
		if (distance >= 5) {
			return -1.0;
		}
		double remainingBattery;
		double currentBatteryLevel = batteryLevel;
		double distanceTravelCapacity = currentBatteryLevel * 0.05;
		if (distance > distanceTravelCapacity) {
			System.out.println(
					"Error, Battery too low to travel the current distance, total distance travelled before battery dead  = "
							+ distanceTravelCapacity);
			return -3.0;
		} else {
			double batteryConsumed = distance * 0.2;
			remainingBattery = (currentBatteryLevel / 100) - batteryConsumed;
			return remainingBattery * 100;
		}
	}

	private Double remainingBatteryWithWeight(double distance, double weight) {
		double remainingBattery;
		if (distance >= 5) {
			return -1.0;
		} else {
			double batteryConsumed = (distance * 0.2) + weight * 0.02;
			remainingBattery = 1 - batteryConsumed;
			if (remainingBattery < 0) {
				return -2.0;
			} else {
				return remainingBattery * 100;
			}
		}
	}
	@Override
	public ResponseModel setErrorMessage(String e) {
		ResponseModel res = new ResponseModel();
		res.setMessage("Error occured : " +e);
		return res;
		
	}
	@Override
	public ResponseModel getRobotStatus(double batteryLevel, double loadWeight, double distance) {
		ResponseModel response = new ResponseModel();
		try {
			if (checkLoadWeight(loadWeight)) {
				response.setMessage("OverWeight");
			} else {
				if (loadWeight == 0.0) {
					if (remainingBatteryNoWeight(distance, batteryLevel) == -1.0) {
						response.setMessage("Distance is greater than the capacity of the robot");
					} else if (remainingBatteryNoWeight(distance, batteryLevel) == -3.0) {
						response.setMessage("Battery Dead before the distance is travelled");
					} else {
						response.setMessage("Battery remaining : " + remainingBatteryNoWeight(distance, batteryLevel));
					}

				} else {
					if (remainingBatteryWithWeight(distance, loadWeight) == -2.0) {
						response.setMessage("Battery died before the distance was completely travelled");
					} else {
						response.setMessage("Battery Remaining : " + remainingBatteryWithWeight(distance, loadWeight));
					}

				}
			}
			if (checkBatteryLevel(batteryLevel))
				response.setBatteryStatus("Battery Low");
			else
				response.setBatteryStatus("Battery OK");	
		}
		catch(Exception e) {
			response.setMessage("Internal error occured");
			e.printStackTrace();
		}
//		if (checkLoadWeight(loadWeight)) {
//			response.setMessage("OverWeight");
//		} else {
//			if (loadWeight == 0.0) {
//				if (remainingBatteryNoWeight(distance, batteryLevel) == -1.0) {
//					response.setMessage("Distance is greater than the capacity of the robot");
//				} else if (remainingBatteryNoWeight(distance, batteryLevel) == -3.0) {
//					response.setMessage("Battery Dead before the distance is travelled");
//				} else {
//					response.setMessage("Battery remaining : " + remainingBatteryNoWeight(distance, batteryLevel));
//				}
//
//			} else {
//				if (remainingBatteryWithWeight(distance, loadWeight) == -2.0) {
//					response.setMessage("Battery died before the distance was completely travelled");
//				} else {
//					response.setMessage("Battery Remaining : " + remainingBatteryWithWeight(distance, loadWeight));
//				}
//
//			}
//		}
//		if (checkBatteryLevel(batteryLevel))
//			response.setBatteryStatus("Battery Low");
//		else
//			response.setBatteryStatus("Battery OK");

		return response;
	}

	@Override
	public ResponseModel getPrice() {
		ResponseEntity<String> s = restTemplate.getForEntity("http://localhost:8089/getbarcode", String.class);
		ResponseModel response = new ResponseModel();
		response.setMessage(s.getBody());
		return response;
	}

}
