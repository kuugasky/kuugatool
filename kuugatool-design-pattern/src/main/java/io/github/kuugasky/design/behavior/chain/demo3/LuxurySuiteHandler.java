package io.github.kuugasky.design.behavior.chain.demo3;

import lombok.AllArgsConstructor;

/**
 * CertainHotelHandler
 * <p>
 * 责任链具体处理者-豪華套房
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:42
 */
@AllArgsConstructor
public class LuxurySuiteHandler extends Handler {

    private final String level;

    @Override
    public boolean handelRequest(String level, String name) {
        if (this.level.equals(level)) {
            System.out.println("客人為國務卿級別，請在豪華套房下榻");
            return true;
        }
        return super.checkNext(level, name);
    }

}
