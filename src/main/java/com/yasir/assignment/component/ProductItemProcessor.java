package com.yasir.assignment.component;

import com.yasir.assignment.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    // I make this simple by assuming that input data object and required data object are same parameters
    // In many cases input file objects are different then output ItemProcessor<UserInput, UserOutput>

    @Override
    public Product process(Product item) throws Exception {
       // here we can do any adaptation if required.
        final String name = item.getName().toUpperCase();
        final String shortName = item.getShortName().toUpperCase();
        final Product transformedProduct = new Product(name,shortName,item.getPrice());
        log.debug("Adaptation Product: item{}, product{}", item, transformedProduct);
        return transformedProduct;
    }
}
