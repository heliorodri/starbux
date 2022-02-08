package com.heliorodri.starbux.domain.drink;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DrinkServiceTest {

  private static DrinkRepository repository;
  private static DrinkService service;

  @BeforeAll
  public static void setUp() {
    repository = mock(DrinkRepository.class);
    service = new DrinkService(repository);
  }

  @Test
  public void itShouldSaveDrink() {
    long id = 23L;
    Drink drink = buildDrink();

    when(repository.save(drink)).thenAnswer(invocation -> {
      drink.setId(id);
      return drink;
    });

    Drink savedDrink = service.save(drink);

    assertEquals(id, savedDrink.getId());
    assertEquals(drink.getName(), savedDrink.getName());
    assertEquals(drink.getPrice(), savedDrink.getPrice());

    verify(repository, times(1)).save(drink);
  }

  @Test
  public void itShouldDeleteDrink() {
    Long id = 10L;

    doNothing().when(repository).deleteById(id);

    service.delete(id);

    verify(repository, times(1)).deleteById(id);
  }

  @Test
  public void itShouldFindAll() {
    final List<Drink> drinks = List.of(buildDrink());

    when(repository.findAll()).thenReturn(drinks);

    List<Drink> foundDrinks = service.findAll();

    assertNotNull(foundDrinks);
    assertEquals(drinks.get(0).getId(), foundDrinks.get(0).getId());
    assertEquals(drinks.get(0).getName(), foundDrinks.get(0).getName());
    assertEquals(drinks.get(0).getPrice(), foundDrinks.get(0).getPrice());

    verify(repository, times(1)).findAll();
  }

  @Test
  public void shouldThrowExceptionWhenTryingToSaveNullDrink() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.save(null));

    assertEquals("drink is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenTryingToDeleteNullId() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.delete(null));

    assertEquals("id is marked non-null but is null", exception.getMessage());
  }

  private Drink buildDrink() {
    return Drink.builder()
        .name("drink")
        .price(1.5)
        .build();
  }

}