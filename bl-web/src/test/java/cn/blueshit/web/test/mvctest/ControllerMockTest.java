package cn.blueshit.web.test.mvctest;

import cn.blueshit.web.controller.ControllerBegin;
import cn.blueshit.web.po.OrderTable;
import cn.blueshit.web.service.TestOrderService;
import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by zhaoheng on 16/10/10.
 *
 * @WebAppConfiguration: 表明该类会使用web应用程序的默认根目录来载入ApplicationContext,
 * 默认的更目录是"src/main/webapp", 如果需要更改这个更目录可以修改该注释的value值
 * @RunWith: 使用 Spring-Test 框架
 * @ContextConfiguration(location = ): 指定需要加载的spring配置文件的地址
 */
/*@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})*/
public class ControllerMockTest {


    private MockMvc mockMvc;

    /*
    *
    * @Mock: 需要被Mock的对象
     *@InjectMocks: 需要将Mock对象注入的对象, 此处就是Controller
    * */

    @Mock
    private TestOrderService testOrderService;

    @InjectMocks
    private ControllerBegin controllerBegin;


    @Before
    public void before() {
        //初始化mock对象
        MockitoAnnotations.initMocks(this);
        Mockito.reset(testOrderService); // 重置mock对象

        mockMvc = MockMvcBuilders.standaloneSetup(controllerBegin).build();

    }


    @Test
    public void testCategoryManage() throws Exception {
        OrderTable orderTable = new OrderTable();
        orderTable.setId(111);
        //定义方法行为
        when(testOrderService.testSelectByOrderId(orderTable)).thenReturn(new OrderTable());
        // 构造http请求及期待响应行为
        this.mockMvc.perform(post("/testSelect").accept(MediaType.APPLICATION_JSON)
                .param("orderId", "111")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

}
