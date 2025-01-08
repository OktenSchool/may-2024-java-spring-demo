package org.okten.may2024.demo.mapper;

import org.mapstruct.Mapper;
import org.okten.may2024.demo.dto.ProductDto;
import org.okten.may2024.demo.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapToDto(Product product);
}
