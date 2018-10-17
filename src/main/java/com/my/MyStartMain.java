package com.my;

import com.my.utils.AutoCreateDomain;

/**
 * description:
 *
 * @author siao.qian@hand-china.com
 * @date 18/10/17 10:02
 * lastUpdateBy: siao.qian@hand-china.com
 * lastUpdateDate: 18/10/17
 */
public class MyStartMain {

    public static void main(String[] args) {
        AutoCreateDomain.createDomain("event_reply");
        AutoCreateDomain.createDomain("event_order");
    }
}
