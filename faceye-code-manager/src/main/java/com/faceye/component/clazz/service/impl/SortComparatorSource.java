package com.faceye.component.clazz.service.impl;

import java.io.IOException;

import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;

public class SortComparatorSource extends FieldComparatorSource {

	@Override
	public FieldComparator<?> newComparator(String fieldname, int numHits, int sortPos, boolean reversed) throws IOException {
		return new SortComparator(fieldname,numHits);
	}

}
