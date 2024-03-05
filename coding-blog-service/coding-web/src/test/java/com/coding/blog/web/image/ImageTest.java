package com.coding.blog.web.image;

import org.junit.jupiter.api.Test;

/**
 * @User Administrator
 * @CreateTime 2024/1/6 17:50
 * @className com.coding.blog.web.image.ImageTest
 */

public class ImageTest {

    @Test
    public void osName(){
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
    }
}
