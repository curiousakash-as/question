package com.robot.prototype.robot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class) //not needed with junit5
@SpringBootTest
@AutoConfigureMockMvc

//@ActiveProfiles(profiles=SpringProfile)
//@WebAppConfiguration
//@WebMvcTest(RobotController.class)
class RobotApplicationTests {
	@Autowired
	public MockMvc mockMvc;
	@Test
	  public void checkIfOverWeight() throws Exception { 
		  String uri = "/robot?batteryLevel={id}&loadWeight= {id1}&distance= {id2}"; 
				mockMvc.perform(get(uri, 100, 12, 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("OverWeight"));
	  
	  }
	@Test
	public void checkWithDistanceAndWeight() throws Exception { 
		  String uri = "/robot?batteryLevel={id}&loadWeight= {id1}&distance= {id2}"; 
				mockMvc.perform(get(uri, 100, 3, 2)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Battery Remaining : 54.0"));
	  
	  }
	@Test
	public void checkWithDistance() throws Exception { 
		  String uri = "/robot?batteryLevel={id}&loadWeight= {id1}&distance= {id2}"; 
				mockMvc.perform(get(uri, 100, 0, 3.5)).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Battery remaining : 29.999999999999993"));
	  
	  }
	
	
}

//intject mock and mock
