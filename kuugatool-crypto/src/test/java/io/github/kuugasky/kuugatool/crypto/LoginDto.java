package io.github.kuugasky.kuugatool.crypto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * LoginDto
 *
 * @author kuuga
 * @since 2023/3/13-03-13 17:58
 */
@Data
public class LoginDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String phone;
    private String name;
    private Date createTime;

    private String token;
    private long version;

}
