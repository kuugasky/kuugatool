package io.github.kuugasky.kuugatool.json.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * PersonPositionChangeDiffDto
 *
 * @author zhongyuan
 * @since 2021/12/14
 */
@Data
public class PersonPositionChangeDiffDto implements Serializable {

    private String oldOrgId;

    private String oldPositionId;

    private String oldLevelId;

    private String oldCompanyOrgId;

    private String oldAllianceCompanyId;

    private String currentOrgId;

    private String currentPositionId;

    private String currentLevelId;

    private String currentCompanyOrgId;

    private String currentAllianceCompanyId;

}
