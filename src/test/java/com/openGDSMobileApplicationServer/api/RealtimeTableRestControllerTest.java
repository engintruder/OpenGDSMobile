package com.openGDSMobileApplicationServer.api;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.openGDSMobileApplicationServer.TestUtil; 
import com.openGDSMobileApplicationServer.service.TableService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/resources/webmapping/spring/context-*.xml",
		"file:src/main/webapp/WEB-INF/config/webmapping/context-*.xml"
})
@WebAppConfiguration
public class RealtimeTableRestControllerTest {

	@Mock
	@Qualifier("realtimeTable")
	TableService ts;
	
	@InjectMocks
	RealtimeTableRestController realtimeTableRestController;
	
    private MockMvc mockMvc;

	@Before
	public void setup() throws Exception{

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        
		mockMvc = MockMvcBuilders.standaloneSetup(realtimeTableRestController)
				.setViewResolvers(viewResolver)
				.build();
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRealtimeSearchTable() throws Exception {
		when(ts.searchTable(isA(JSONObject.class))).thenReturn(Mockito.anyList());

		mockMvc.perform(post("/api/realtimeInfoSearch.do")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.param("column", "userid"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void testRealtimeInsertTable() throws Exception {
		when(ts.insertData(isA(JSONObject.class))).thenReturn(1);
		mockMvc.perform(post("/api/realtimeInfoInsert.do")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.param("session", "0"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void testRealtimeDeleteTable() throws Exception {
		when(ts.deleteData(isA(JSONObject.class))).thenReturn(1);
		mockMvc.perform(post("/api/realtimeInfoDelete.do")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.param("session", "0"))
			.andExpect(status().isOk())
			.andDo(print());
	}

}
