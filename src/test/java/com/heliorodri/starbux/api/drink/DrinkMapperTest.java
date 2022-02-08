package com.heliorodri.starbux.api.drink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.heliorodri.starbux.domain.drink.Drink;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DrinkMapperTest {

  private static final long ID = 1L;
  private static final String NAME = "water";
  private static final double PRICE = 2.5;
  
  private static DrinkMapper mapper;

  @BeforeAll
  public static void setUp() {
    mapper = new DrinkMapper();
  }

  @Test
  public void itShouldMapDtoToEntity() {
    final DrinkDto drinkToMap = buildDto();

    final Drink mappedDrink = mapper.toEntity(drinkToMap);

    assertNotNull(mappedDrink);
    assertEquals(drinkToMap.getId(), mappedDrink.getId());
    assertEquals(drinkToMap.getName(), mappedDrink.getName());
    assertEquals(drinkToMap.getPrice(), mappedDrink.getPrice());
  }

  @Test
  public void itShouldMapDrinkCreateRequestToEntity() {
    final DrinkCreateRequest request = buildRequest();

    final Drink mappedDrink = mapper.toEntity(request);

    assertNotNull(mappedDrink);
    assertEquals(request.getName(), mappedDrink.getName());
    assertEquals(request.getPrice(), mappedDrink.getPrice());
  }

  @Test
  public void itShouldMapEntityToDto() {
    final Drink entity = buildDrink();

    final DrinkDto mappedDTo = mapper.toDto(entity);

    assertNotNull(mappedDTo);
    assertEquals(entity.getId(), mappedDTo.getId());
    assertEquals(entity.getName(), mappedDTo.getName());
    assertEquals(entity.getPrice(), mappedDTo.getPrice());
  }

  private DrinkCreateRequest buildRequest() {
    return DrinkCreateRequest.builder()
        .name(NAME)
        .price(PRICE)
        .build();
  }

  private Drink buildDrink() {
    return Drink.builder()
        .id(ID)
        .name(NAME)
        .price(PRICE)
        .build();
  }

  public DrinkDto buildDto() {
    return DrinkDto.builder()
        .id(ID)
        .name(NAME)
        .price(PRICE)
        .build();
  }


}