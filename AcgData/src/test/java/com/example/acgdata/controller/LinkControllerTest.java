package com.example.acgdata.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 接口层集成测试：用友情链接 + 用户两个模块覆盖
 * 「统一响应体 / 匿名可读 / 401 / 403 / 参数校验 / 管理员增删」这些公共能力。
 * 依赖本地 MySQL（acg_fans），启动时会自动执行 db/schema 与 db/data 下的脚本。
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("友情链接接口集成测试")
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("匿名 GET：返回统一响应体，且带出种子数据")
    void getLinks_shouldReturnUnifiedResult() throws Exception {
        mockMvc.perform(get("/api/links/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").isNotEmpty())
                .andExpect(jsonPath("$.data[0].url").exists())
                .andExpect(jsonPath("$.data[0].link").exists());
    }

    @Test
    @DisplayName("未登录写入：返回 401")
    void insert_withoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(post("/api/links/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"anon\",\"targetUrl\":\"https://example.com\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));
    }

    @Test
    @DisplayName("参数校验失败：返回 code 400 与字段级提示")
    void insert_withInvalidBody_shouldReturnParamError() throws Exception {
        mockMvc.perform(post("/api/links/insert")
                        .header("Authorization", "Bearer " + adminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"targetUrl\":\"\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    @DisplayName("普通用户改数据：返回 403")
    void update_withNormalUser_shouldReturn403() throws Exception {
        mockMvc.perform(put("/api/links/update/1")
                        .header("Authorization", "Bearer " + normalUserToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"hack\",\"targetUrl\":\"https://example.com\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    @DisplayName("管理员新增后删除：全链路走通")
    void insertThenDelete_asAdmin_shouldSucceed() throws Exception {
        String token = adminToken();

        String created = mockMvc.perform(post("/api/links/insert")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"unit-test-link\",\"iconUrl\":\"https://example.com/i.png\","
                                + "\"targetUrl\":\"https://example.com\",\"sort\":98}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        long id = objectMapper.readTree(created).get("data").get("id").asLong();

        mockMvc.perform(delete("/api/links/delete/" + id).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value("success"));
    }

    private String adminToken() throws Exception {
        return tokenOf("/api/user/login", "{\"username\":\"admin\",\"password\":\"admin123\"}");
    }

    /** 固定账号：第一次注册，之后直接登录，避免每次跑测试都新增用户 */
    private String normalUserToken() throws Exception {
        String payload = "{\"username\":\"e2e_tester\",\"password\":\"test123456\","
                + "\"email\":\"e2e_tester@example.com\"}";
        String registerBody = mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn().getResponse().getContentAsString();

        JsonNode node = objectMapper.readTree(registerBody);
        if (node.get("code").asInt() != 200) {
            return tokenOf("/api/user/login", payload);
        }
        return node.get("data").get("token").asText();
    }

    private String tokenOf(String url, String payload) throws Exception {
        String body = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(body).get("data").get("token").asText();
    }
}
