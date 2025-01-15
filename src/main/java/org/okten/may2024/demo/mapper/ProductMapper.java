package org.okten.may2024.demo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapToDto(Product product);

    Product mapToEntity(ProductDto dto);

    Product updateEntity(@MappingTarget Product entity, ProductDto updateWith);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntityPartially(@MappingTarget Product entity, ProductDto updateWith);
}
