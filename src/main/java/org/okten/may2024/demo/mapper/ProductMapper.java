package org.okten.may2024.demo.mapper;

import org.mapstruct.*;
import org.okten.may2024.api.dto.ProductDto;
import org.okten.may2024.demo.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "availability", source = "productAvailability")
    ProductDto mapToDto(Product product);

    @InheritInverseConfiguration
    Product mapToEntity(ProductDto dto);

    Product updateEntity(@MappingTarget Product entity, ProductDto updateWith);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntityPartially(@MappingTarget Product entity, ProductDto updateWith);
}
