package io.github.kuugasky.kuugatool.core.string;

/**
 * KuugaStringBuilder
 *
 * @author kuuga
 * @since 2021/6/3
 */
public class KuugaStringBuilder {

    private StringBuilder stringBuilder;

    private boolean filterEmpty = false;

    public static KuugaStringBuilder builder() {
        KuugaStringBuilder kuugaStringBuilder = new KuugaStringBuilder();
        kuugaStringBuilder.setStringBuilder(new StringBuilder());
        return kuugaStringBuilder;
    }

    public KuugaStringBuilder append(String text) {
        if (filterEmpty) {
            if (StringUtil.hasText(text)) {
                this.stringBuilder.append(text);
            }
        } else {
            this.stringBuilder.append(text);
        }
        return this;
    }

    public KuugaStringBuilder append(CharSequence text) {
        if (filterEmpty) {
            if (StringUtil.hasText(text)) {
                this.stringBuilder.append(text);
            }
        } else {
            this.stringBuilder.append(text);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.stringBuilder.toString();
    }

    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public KuugaStringBuilder filterEmpty(boolean filterEmpty) {
        this.filterEmpty = filterEmpty;
        return this;
    }

}
