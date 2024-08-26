package com.jayson.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: Jayson_Y
 * @date: 2024/8/26
 * @project: spzx-parent
 */
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserAuthProperties {

    private List<String> noAuthUrls;
}
