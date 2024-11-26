package com.pn.common.params.register;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDeletionReqParam implements Serializable {
    private static final long serialVersionUID = 6675495434939176961L;
    private String username;
}
