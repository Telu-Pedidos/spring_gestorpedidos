package com.api.gestor_pedidos_telu.utils;

import java.text.Normalizer;

public class Slug {
    public static String generateSlug(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }

        // Normaliza o texto, remove acentos e barras
        String normalized = Normalizer.normalize(name.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Remove caracteres de acentuação
                .replaceAll("[/]", ""); // Remove barras

        // Converte para minúsculas, substitui espaços por hífens e remove hífens duplicados
        String slug = normalized.toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("-+", "-");

        return slug;
    }
}
