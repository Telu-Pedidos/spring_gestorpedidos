package com.api.gestor_pedidos_telu.utils;

import java.text.Normalizer;

public class Slug {
    public static String generateSlug(String name) {
        if (name != null && !name.trim().isEmpty()) {
            String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD);
            String slug = normalized.replaceAll("\\p{M}", "");

            return slug.toLowerCase().replaceAll(" ", "-").replaceAll("-+", "-");
        }
        return "";
    }
}
