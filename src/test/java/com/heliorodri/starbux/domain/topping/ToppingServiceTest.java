package com.heliorodri.starbux.domain.topping;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ToppingServiceTest {

  private static ToppingService service;
  private static ToppingRepository repository;

  @BeforeAll
  public static void setUp() {
    repository = mock(ToppingRepository.class);

    service = new ToppingService(repository);
  }

  @Test
  public void itShouldSaveTopping() {
    final Long id = 100L;
    Topping topping = buildTopping();

    when(repository.save(topping)).thenAnswer(invocation -> {
      topping.setId(id);
      return topping;
    });

    final Topping savedTopping = service.create(topping);

    assertEquals(id, savedTopping.getId());
    assertEquals(topping.getName(), savedTopping.getName());
    assertEquals(topping.getPrice(), savedTopping.getPrice());

    verify(repository, times(1)).save(topping);
  }

  @Test
  public void itShouldDeleteToppingById() {
    final Long id = 1L;

    doNothing().when(repository).deleteById(id);

    service.deleteById(id);

    verify(repository, times(1)).deleteById(id);
  }

  @Test
  public void itShouldFindAll() {
    final List<Topping> toppings = List.of(buildTopping());

    when(repository.findAll()).thenReturn(toppings);

    final List<Topping> foundToppings = service.findAll();

    assertNotNull(foundToppings);
    assertEquals(toppings.get(0).getId(), foundToppings.get(0).getId());
    assertEquals(toppings.get(0).getName(), foundToppings.get(0).getName());
    assertEquals(toppings.get(0).getPrice(), foundToppings.get(0).getPrice());

    verify(repository, times(1)).findAll();
  }

  @Test
  public void itShouldThrowExceptionWhenTryingToSaveNullTopping() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(null));

    assertEquals("topping is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenTryingToDeleteNullId() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));

    assertEquals("id is marked non-null but is null", exception.getMessage());
  }

  private Topping buildTopping() {
    return Topping.builder()
        .name("topping")
        .price(1.5)
        .build();
  }

}