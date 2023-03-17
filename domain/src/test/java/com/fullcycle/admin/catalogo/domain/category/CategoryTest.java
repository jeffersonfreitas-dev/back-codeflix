package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
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


    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReturnError() {
        final String exName = null;
        final var exErrorCount = 1;
        final var exErrorMessage = "'name' should not be null";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);
        final var acException = Assertions.assertThrows(DomainException.class,
                () -> acCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(exErrorCount, acException.getErrors().size());
        Assertions.assertEquals(exErrorMessage, acException.getErrors().get(0).message());;
    }
}
