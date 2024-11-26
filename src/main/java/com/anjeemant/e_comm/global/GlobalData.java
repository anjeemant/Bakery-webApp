package com.anjeemant.e_comm.global;

import java.util.ArrayList;
import java.util.List;

import com.anjeemant.e_comm.model.Product;

public class GlobalData {
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}