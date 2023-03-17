package com.fullcycle.admin.catalogo.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParams_whenCallNewCategory_thenInstantiateCategory() {
        final var exName = "Filmes";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);

        Assertions.assertNotNull(acCategory);
        Assertions.assertNotNull(acCategory.getId());
        Assertions.assertEquals(acCategory.getName(), exName);
        Assertions.assertEquals(acCategory.getDescription(), exDescription);
        Assertions.assertEquals(acCategory.isActive(), exIsActive);
        Assertions.assertNotNull(acCategory.getCreatedAt());
        Assertions.assertNotNull(acCategory.getUpdatedAt());
        Assertions.assertNull(acCategory.getDeletedAt());
    }
}
