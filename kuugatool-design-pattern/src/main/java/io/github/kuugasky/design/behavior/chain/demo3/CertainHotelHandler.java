package io.github.kuugasky.design.behavior.chain.demo3;

import lombok.AllArgsConstructor;

/**
 * CertainHotelHandler
 * <p>
 * 责任链具体处理者-旅馆
 *
 * @author kuuga
 * @since 2023/6/8-06-08 09:42
 */
@AllArgsConstructor
public class CertainHotelHandler extends Handler {

    private final String level;

    @Override
    public boolean handelRequest(String level, String name) {
        if (this.level.equals(level)) {
            System.out.println("客人為外交部長級別，請在旅館下榻");
            return true;
        }
        return super.checkNext(level, name);
    }

}
