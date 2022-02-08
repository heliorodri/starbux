package com.heliorodri.starbux.api.topping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.heliorodri.starbux.domain.topping.Topping;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ToppingMapperTest {

  private static final long ID = 1L;
  private static final String NAME = "milk";
  private static final double PRICE = 1.5;

  private static ToppingMapper mapper;

  @BeforeAll
  public static void setUp() {
    mapper = new ToppingMapper();
  }

  @Test
  public void itShouldMapEntityToDto() {
    final Topping entity = buildEntity();

    final ToppingDto mappedDto = mapper.toDto(entity);

    assertNotNull(mappedDto);
    assertEquals(mappedDto.getId(), entity.getId());
    assertEquals(mappedDto.getName(), entity.getName());
    assertEquals(mappedDto.getPrice(), entity.getPrice());
  }

  @Test
  public void itShouldMapDtoToEntity() {
    final ToppingDto dto = buildDto();

    final Topping mappedEntity = mapper.toEntity(dto);

    assertNotNull(mappedEntity);
    assertEquals(mappedEntity.getId(), dto.getId());
    assertEquals(mappedEntity.getName(), dto.getName());
    assertEquals(mappedEntity.getPrice(), dto.getPrice());
  }

  @Test
  public void itShouldMapToppingCreateRequestToEntity() {
    final ToppingCreateRequest request = buildRequest();

    final Topping mappedEntity = mapper.toEntity(request);

    assertNotNull(mappedEntity);
    assertEquals(mappedEntity.getName(), request.getName());
    assertEquals(mappedEntity.getPrice(), request.getPrice());
  }

  @Test
  public void itShouldMapEntityListToDtoList() {
    final List<Topping> entities = List.of(buildEntity());

    final List<ToppingDto> dtos = mapper.toDtoList(entities);

    assertNotNull(dtos);
    assertEquals(entities.get(0).getId(), dtos.get(0).getId());
    assertEquals(entities.get(0).getName(), dtos.get(0).getName());
    assertEquals(entities.get(0).getPrice(), dtos.get(0).getPrice());
  }

  @Test
  public void itShouldMapDtoListToEntityList() {
    final List<ToppingDto> dtos = List.of(buildDto());

    final List<Topping> entities = mapper.toEntityList(dtos);

    assertNotNull(entities);
    assertEquals(dtos.get(0).getId(), entities.get(0).getId());
    assertEquals(dtos.get(0).getName(), entities.get(0).getName());
    assertEquals(dtos.get(0).getPrice(), entities.get(0).getPrice());
  }

  private ToppingCreateRequest buildRequest() {
    return ToppingCreateRequest.builder()
        .name(NAME)
        .price(PRICE)
        .build();
  }

  private Topping buildEntity() {
    return Topping.builder()
        .id(ID)
        .name(NAME)
        .price(PRICE)
        .build();
  }

  private ToppingDto buildDto() {
    return ToppingDto.builder()
        .id(ID)
        .name(NAME)
        .price(PRICE)
        .build();
  }

}