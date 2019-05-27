package com.chart.form;

import java.util.List;
import java.util.Map;

public class InfoChart {
	private String TypeChart;
	private String RangeTime;
	private String NamePositionData;
	private String NameStatusData;
	public String getTypeChart() {
		return TypeChart;
	}
	public void setTypeChart(String typeChart) {
		TypeChart = typeChart;
	}
	public String getRangeTime() {
		return RangeTime;
	}
	public void setRangeTime(String rangeTime) {
		RangeTime = rangeTime;
	}
	public String getNamePositionData() {
		return NamePositionData;
	}
	public void setNamePositionData(String namePositionData) {
		NamePositionData = namePositionData;
	}
	public String getNameStatusData() {
		return NameStatusData;
	}
	public void setNameStatusData(String nameStatusData) {
		NameStatusData = nameStatusData;
	}
	public InfoChart(String typeChart, String rangeTime, String namePositionData, String nameStatusData) {
		super();
		TypeChart = typeChart;
		RangeTime = rangeTime;
		NamePositionData = namePositionData;
		NameStatusData = nameStatusData;
	}
	public InfoChart() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "InfoChart [TypeChart=" + TypeChart + ", RangeTime=" + RangeTime + ", NamePositionData="
				+ NamePositionData + ", NameStatusData=" + NameStatusData + "]";
	}
	
	
}
