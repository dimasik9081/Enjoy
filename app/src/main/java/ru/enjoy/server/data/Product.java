package ru.enjoy.server.data;

public class Product {
	public int id;
	public String name;
	transient public int type;
	public String shortDesc;
	public String comment;
	public String url;
	public String width;
	public String height;
	public String unitForPrice;
	public String photoDesc;
	public ProductSostavComponent[] sostav;
	public Variant[] variants;
	public ProductSpecialOffer[] specialOffers;
	public ProductImages[] images;
	public ProductParameterCommonValue[] paramCommonValues;
	public ProductParameterVariant[] parameterVariants;
	public ProductParameterVariation[] parameterVariations;
	
}
