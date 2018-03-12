package com.warumono.client.enums.converters;

public interface AbstractEnumeratable<E>
{
	String getToDatabaseColumn(E attribute);
	E getToEntityAttribute(String dbData);
}
