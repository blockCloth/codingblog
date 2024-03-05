package com.coding.blog.web.courage;

import org.junit.jupiter.api.Test;

/**
 * @User Administrator
 * @CreateTime 2024/1/9 11:50
 * @className com.coding.blog.web.courage.JavaTest
 */
public class JavaTest {

    @Test
    public void jiujiu() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + i * j + "\t");
            }
            System.out.println();
        }
    }

    @Test
    public void shuixianhua() {
        /*for (int i = 100; i < 1000; i++) {
            //获取三位数
            int a = i / 100 % 10; //百位
            int b = i / 10 % 10; //十位
            int c = i % 10; //个位
            if (a*a*a + b*b*b + c*c*c == i){
                System.out.println("水仙花数：" + i);
            }
        }*/

        int x = 1, y = 1;
        if (x == 1)
            y = x + 1;
        else if (y == 2)
            x = y + 1;
        else
            y = 0;
        System.out.println(x);
        System.out.println(y);
    }


}
