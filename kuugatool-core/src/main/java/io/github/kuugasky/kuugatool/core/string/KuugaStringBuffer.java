package io.github.kuugasky.kuugatool.core.string;

/**
 * KuugaStringBuilder
 *
 * @author kuuga
 * @since 2021/6/3
 */
public class KuugaStringBuffer {

    private StringBuffer stringBuffer;

    private boolean filterEmpty = false;

    public static KuugaStringBuffer builder() {
        KuugaStringBuffer kuugaStringBuilder = new KuugaStringBuffer();
        kuugaStringBuilder.setStringBuffer(new StringBuffer());
        return kuugaStringBuilder;
    }

    public KuugaStringBuffer append(String text) {
        if (this.filterEmpty) {
            if (StringUtil.hasText(text)) {
                this.stringBuffer.append(text);
            }
        } else {
            this.stringBuffer.append(text);
        }
        return this;
    }

    public KuugaStringBuffer append(CharSequence text) {
        if (filterEmpty) {
            if (StringUtil.hasText(text)) {
                this.stringBuffer.append(text);
            }
        } else {
            this.stringBuffer.append(text);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.stringBuffer.toString();
    }

    public void setStringBuffer(StringBuffer stringBuffer) {
        this.stringBuffer = stringBuffer;
    }

    public KuugaStringBuffer filterEmpty(boolean filterEmpty) {
        this.filterEmpty = filterEmpty;
        return this;
    }

}
