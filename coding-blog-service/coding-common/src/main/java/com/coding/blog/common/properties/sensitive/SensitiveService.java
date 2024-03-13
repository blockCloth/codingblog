package com.coding.blog.common.properties.sensitive;

import com.coding.blog.common.autoconf.DynamicConfigContainer;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllowSystem;
import com.github.houbb.sensitive.word.support.deny.WordDenySystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @User Administrator
 * @CreateTime 2024/3/10 9:45
 * @className com.coding.blog.common.properties.sensitive.SensitiveService
 */
@Slf4j
@Service
public class SensitiveService {
    private SensitiveProperty sensitiveConfig;

    private volatile SensitiveWordBs sensitiveWordBs;

    public SensitiveService(DynamicConfigContainer dynamicConfigContainer, SensitiveProperty sensitiveConfig) {
        this.sensitiveConfig = sensitiveConfig;
        dynamicConfigContainer.registerRefreshCallback(sensitiveConfig, this::refresh);
    }

    @PostConstruct
    public void refresh() {
        IWordDeny deny = () -> {
            List<String> sub = WordDenySystem.getInstance().deny();
            sub.addAll(sensitiveConfig.getDeny());
            return sub;
        };

        IWordAllow allow = () -> {
            List<String> sub = WordAllowSystem.getInstance().allow();
            sub.addAll(sensitiveConfig.getAllow());
            return sub;
        };
        sensitiveWordBs = SensitiveWordBs.newInstance()
                .wordDeny(deny)
                .wordAllow(allow)
                .init();
        log.info("敏感词初始化完成!");
    }

    /**
     * 判断是否包含敏感词
     *
     * @param txt
     * @return
     */
    public boolean contains(String txt) {
        if (BooleanUtils.isTrue(sensitiveConfig.getEnable())) {
            return sensitiveWordBs.contains(txt);
        }
        return false;
    }


    /**
     * 敏感词替换
     *
     * @param txt
     * @return
     */
    public String replace(String txt) {
        if (BooleanUtils.isTrue(sensitiveConfig.getEnable())) {
            return sensitiveWordBs.replace(txt);
        }
        return txt;
    }

    /**
     * 查询文本中所有命中的敏感词
     *
     * @param txt 校验文本
     * @return 命中的敏感词
     */
    public List<String> findAll(String txt) {
        return sensitiveWordBs.findAll(txt);
    }
}
