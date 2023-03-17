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
        Assertions.assertEquals(exErrorMessage, acException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReturnError() {
        final var exName = "     ";
        final var exErrorCount = 1;
        final var exErrorMessage = "'name' should not be empty";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);
        final var acException = Assertions.assertThrows(DomainException.class,
                () -> acCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(exErrorCount, acException.getErrors().size());
        Assertions.assertEquals(exErrorMessage, acException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvaliNameLessThan3_whenCallNewCategoryAndValidate_thenShouldReturnError() {
        final var exName = "F1 ";
        final var exErrorCount = 1;
        final var exErrorMessage = "'name' should be between 3 and 255 characters";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);
        final var acException = Assertions.assertThrows(DomainException.class,
                () -> acCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(exErrorCount, acException.getErrors().size());
        Assertions.assertEquals(exErrorMessage, acException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvaliNameMoreThan255_whenCallNewCategoryAndValidate_thenShouldReturnError() {
        final var exName = """
                Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem
                sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e
                os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum sobreviveu não só a cinco séculos,
                como também ao salto para a editoração eletrônica
                """;
        final var exErrorCount = 1;
        final var exErrorMessage = "'name' should be between 3 and 255 characters";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);
        final var acException = Assertions.assertThrows(DomainException.class,
                () -> acCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(exErrorCount, acException.getErrors().size());
        Assertions.assertEquals(exErrorMessage, acException.getErrors().get(0).message());
    }

    @Test
    public void givenAnValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldEmpty() {
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
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
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldOk() {
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = false;

        final var acCategory = Category.newCategory(exName, exDescription, exIsActive);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(acCategory);
        Assertions.assertNotNull(acCategory.getId());
        Assertions.assertEquals(acCategory.getName(), exName);
        Assertions.assertEquals(acCategory.getDescription(), exDescription);
        Assertions.assertEquals(acCategory.isActive(), exIsActive);
        Assertions.assertNotNull(acCategory.getCreatedAt());
        Assertions.assertNotNull(acCategory.getUpdatedAt());
        Assertions.assertNotNull(acCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactive_thenReturnCategoryDeactivated() {
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = false;

        final var acCategory = Category.newCategory(exName, exDescription, true);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
        final var updatedAt = acCategory.getUpdatedAt();

        Assertions.assertTrue(acCategory.isActive());
        Assertions.assertNull(acCategory.getDeletedAt());

        final var actualCategory = acCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(acCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), exName);
        Assertions.assertEquals(actualCategory.getDescription(), exDescription);
        Assertions.assertEquals(actualCategory.isActive(), exIsActive);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }


    @Test
    public void givenAValidInactiveCategory_whenCallActive_thenReturnCategoryActivated() {
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory(exName, exDescription, false);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
        final var updatedAt = acCategory.getUpdatedAt();
        final var createdAt = acCategory.getCreatedAt();

        Assertions.assertFalse(acCategory.isActive());
        Assertions.assertNotNull(acCategory.getDeletedAt());

        final var actualCategory = acCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(acCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), exName);
        Assertions.assertEquals(actualCategory.getDescription(), exDescription);
        Assertions.assertEquals(actualCategory.isActive(), exIsActive);
        Assertions.assertEquals(actualCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = true;

        final var acCategory = Category.newCategory("exName", "exDescription", false);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
        final var updatedAt = acCategory.getUpdatedAt();
        final var createdAt = acCategory.getCreatedAt();

        final var actualCategory = acCategory.update(exName, exDescription, exIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(acCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), exName);
        Assertions.assertEquals(actualCategory.getDescription(), exDescription);
        Assertions.assertEquals(actualCategory.isActive(), exIsActive);
        Assertions.assertEquals(actualCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateInactive_thenReturnCategoryUpdated(){
        final var exName = "Teste";
        final var exDescription = "Some description";
        final var exIsActive = false;

        final var acCategory = Category.newCategory("exName", "exDescription", true);

        Assertions.assertDoesNotThrow(() -> acCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(acCategory.isActive());
        Assertions.assertNull(acCategory.getDeletedAt());

        final var updatedAt = acCategory.getUpdatedAt();
        final var createdAt = acCategory.getCreatedAt();

        final var actualCategory = acCategory.update(exName, exDescription, exIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(acCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), exName);
        Assertions.assertEquals(actualCategory.getDescription(), exDescription);
        Assertions.assertEquals(actualCategory.getCreatedAt(), createdAt);
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertFalse(acCategory.isActive());
        Assertions.assertNotNull(acCategory.getDeletedAt());
    }
}
