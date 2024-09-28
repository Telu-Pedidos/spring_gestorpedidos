package com.api.gestor_pedidos_telu.utils;

import static com.api.gestor_pedidos_telu.utils.Regex.PHONE_PATTERN;

public class Phone {
    public static boolean isPhoneValid(String phone) {
        return phone == null || phone.isEmpty() || phone.matches(PHONE_PATTERN);
    }
}
