package com.pn.common.reqParams.id;

import lombok.Data;

import java.io.Serializable;

/**
 * 只带有id的参数
 */
@Data
public class IdParams implements Serializable {
    private static final long serialVersionUID = -8259275075368492486L;

    private Long id;
}
