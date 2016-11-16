package com.faceye.component.clazz.service.impl;

import java.io.IOException;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.FieldCache.Ints;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortComparator extends FieldComparator<String> {

	private Logger logger = LoggerFactory.getLogger(SortComparator.class);
	// 字段名称
	private String fieldName;
	// 最大返回值个数，小于总共查询的返回数 查询出来排序好的假设是100 可能只需要返回10条
	private int numHits;
	// 查询出来总共的数组
	private String[] currentReaderValues;
	// 需要返回的数组
	private String[] values;

	private String bottom;

	public SortComparator(String fieldName, int numHits) {
		this.fieldName = fieldName;
		this.values = new String[numHits];
	}

	@Override
	public FieldComparator<String> setNextReader(AtomicReaderContext context) throws IOException {
		AtomicReader reader = context.reader();
		// 利用FieldCache取到Term的数据，哪怕你的Store设置的是False也会False
		BinaryDocValues bdv = FieldCache.DEFAULT.getTerms(reader, fieldName, false);
//		Ints s = FieldCache.DEFAULT.getInts(reader, "boots", false);
		Ints s = FieldCache.DEFAULT.getInts(reader, "boots", false);
		int maxId = reader.maxDoc();
		currentReaderValues = new String[maxId];
		logger.debug(">>FaceYe sort -->maxId:" + maxId);
		for (int i = 0; i < maxId; i++) {
			logger.debug(">>FaceYe --Sort -->:" + s.get(i) + "ii");
			BytesRef bf = bdv.get(i);
			String value = bf.utf8ToString();
			currentReaderValues[i] = value;
		}
		return this;
	}

	@Override
	public int compare(int slot1, int slot2) {
		String v1 = value(slot1);
		String v2 = value(slot2);
		logger.debug(">>FaceYe --> v1:" + v1 + "v2" + v2);
		if (v1.length() > v2.length()) {
			return 1;
		} else if (v1.length() < v2.length()) {
			return -1;
		}
		return 0;
	}

	@Override
	public void setBottom(int slot) {
		logger.debug(">>FaceYe --> sort -->setBottom#" + "slot:" + slot);
		this.bottom = this.values[slot];
	}

	@Override
	public void setTopValue(String value) {

	}

	@Override
	public int compareBottom(int doc) throws IOException {
		String val2 = this.currentReaderValues[doc];
		String tempBottom = this.bottom;
		logger.debug(">>FaceYe --> sort :val2:" + val2 + "####tempBottom:" + tempBottom);
		if (val2.length() < tempBottom.length()) {
			return 1;
		} else if (val2.length() > tempBottom.length()) {
			return -1;
		}
		return 0;
	}

	@Override
	public int compareTop(int doc) throws IOException {
		return 0;
	}

	@Override
	public void copy(int slot, int doc) throws IOException {
		logger.debug(">>FaceYe --> Sort -->this.currentReaderValues[doc]" + this.currentReaderValues[doc]);
		this.values[slot] = this.currentReaderValues[doc];
	}

	@Override
	public String value(int slot) {
		return values[slot];
	}

}
