package cn.blueshit.web.test.mvctest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zhaoheng on 16/10/11.
 *
 * @WebAppConfiguration: 表明该类会使用web应用程序的默认根目录来载入ApplicationContext,
 * 默认的更目录是"src/main/webapp", 如果需要更改这个更目录可以修改该注释的value值
 * @RunWith: 使用 Spring-Test 框架
 * @ContextConfiguration(location = ): 指定需要加载的spring配置文件的地址
 * 模拟真实的webmvc环境
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class WebControllerTest {


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test() throws Exception {
        String orderId = "6208579551349567619";
        this.mockMvc
                .perform(post("/testSelect")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("orderId", orderId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }


}
