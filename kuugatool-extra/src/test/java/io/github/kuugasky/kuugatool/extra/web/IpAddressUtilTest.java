package io.github.kuugasky.kuugatool.extra.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class IpAddressUtilTest {

    @Test
    public void getIpAddr() {
        System.out.println(Arrays.toString(IpAddressUtil.getIpAddr(CommonWebUtil.getRequest())));
    }

}