package io.github.kuugasky.design.apply.demo4;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WorkExperience
 *
 * @author kuuga
 * @since 2023/10/13 12:08
 */
@Data
@AllArgsConstructor
public class WorkExperience implements Cloneable {

    private String timeArea;
    private String company;

    @Override
    public WorkExperience clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (WorkExperience) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
