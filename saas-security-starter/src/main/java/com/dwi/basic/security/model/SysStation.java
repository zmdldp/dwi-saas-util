package com.dwi.basic.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 实体类
 *
 * </p>
 *
 * @author dwi
 * @since 2020-07-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class SysStation {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 组织ID
     * #c_org
     */
    private Long orgId;

    /**
     * 排序
     */
    private Integer sortValue;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 描述
     */
    private String describe;


}
