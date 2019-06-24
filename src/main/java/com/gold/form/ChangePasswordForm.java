package com.gold.form;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordForm {
    private String password;
    private String newPassword;
}
