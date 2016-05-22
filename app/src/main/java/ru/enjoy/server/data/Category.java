package ru.enjoy.server.data;

import java.util.List;

public class Category {
	public int id;
	public int type;
	public String name;
	public List<ProductAndCategoryPointer> products;
}