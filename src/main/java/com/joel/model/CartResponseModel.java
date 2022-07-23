/**
 * 
 */
package com.joel.model;

import java.util.Collection;

import com.joel.entity.Product;

import lombok.Builder;
import lombok.Getter;

/**
 * @author joel.rubio
 *
 */
@Builder
@Getter
public class CartResponseModel {

	private int id;
	private Collection<Product> products;
}
