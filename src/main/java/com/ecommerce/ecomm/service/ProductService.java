package com.ecommerce.ecomm.service;

import com.ecommerce.ecomm.payload.ProductDTO;
import com.ecommerce.ecomm.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image);
}
