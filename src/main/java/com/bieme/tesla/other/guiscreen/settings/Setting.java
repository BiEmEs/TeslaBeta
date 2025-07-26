package com.bieme.tesla.other.guiscreen.settings;

import com.bieme.tesla.modules.hacks.Module;

import java.util.List;

/**
 * Clase que representa una configuración (Setting) de un módulo,
 * puede ser boolean, slider, combobox o label.
 */
public class Setting {

	private final Module master;

	private final String name;
	private final String tag;

	private boolean boolValue;
	private double sliderValue;
	private double min;
	private double max;
	private List<String> comboBoxValues;
	private String currentValue;
	private String stringValue;

	private final String type;

	// Visibilidad, por defecto visible
	private boolean visible = true;

	// Valor por defecto para reset
	private final Object defaultValue;

	// Boolean setting
	public Setting(Module master, String name, String tag, boolean value) {
		this.master = master;
		this.name = name;
		this.tag = tag;
		this.boolValue = value;
		this.type = "boolean";
		this.defaultValue = value;
	}

	// Combobox setting
	public Setting(Module master, String name, String tag, List<String> values, String current) {
		this.master = master;
		this.name = name;
		this.tag = tag;
		this.comboBoxValues = values;
		this.currentValue = current;
		this.type = "combobox";
		this.defaultValue = current;
	}

	// Label (string) setting
	public Setting(Module master, String name, String tag, String value) {
		this.master = master;
		this.name = name;
		this.tag = tag;
		this.stringValue = value;
		this.type = "label";
		this.defaultValue = value;
	}

	// Double slider setting
	public Setting(Module master, String name, String tag, double value, double min, double max) {
		this.master = master;
		this.name = name;
		this.tag = tag;
		this.min = min;
		this.max = max;
		this.sliderValue = clamp(value, min, max);
		this.type = "double";
		this.defaultValue = value;
	}

	// Integer slider setting
	public Setting(Module master, String name, String tag, int value, int min, int max) {
		this.master = master;
		this.name = name;
		this.tag = tag;
		this.min = min;
		this.max = max;
		this.sliderValue = clamp(value, min, max);
		this.type = "int";
		this.defaultValue = value;
	}

	public Module getMaster() {
		return master;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public String getType() {
		return type;
	}

	// Boolean
	public boolean getBoolValue() {
		return boolValue;
	}

	public void setBoolValue(boolean value) {
		this.boolValue = value;
	}

	// Combobox
	public List<String> getComboBoxValues() {
		return comboBoxValues;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String value) {
		if (comboBoxValues != null && comboBoxValues.contains(value)) {
			this.currentValue = value;
		}
	}

	public boolean in(String value) {
		return currentValue.equalsIgnoreCase(value);
	}

	// Label
	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String value) {
		this.stringValue = value;
	}

	// Slider (double/int)
	public double getSliderValue() {
		return sliderValue;
	}

	public int getSliderValueInt() {
		return (int) Math.round(sliderValue);
	}

	public void setSliderValue(double value) {
		this.sliderValue = clamp(value, min, max);
	}

	public void setSliderValue(int value) {
		this.sliderValue = clamp(value, min, max);
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public int getMinInt() {
		return (int) min;
	}

	public int getMaxInt() {
		return (int) max;
	}

	/** Clamp value between min and max. */
	private double clamp(double val, double min, double max) {
		return Math.max(min, Math.min(max, val));
	}

	/** Si quieres controlar visibilidad del setting. */
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/** Resetea el valor al valor por defecto. */
	public void resetToDefault() {
		switch (type) {
			case "boolean":
				this.boolValue = (boolean) defaultValue;
				break;
			case "combobox":
				this.currentValue = (String) defaultValue;
				break;
			case "label":
				this.stringValue = (String) defaultValue;
				break;
			case "double":
			case "int":
				this.sliderValue = ((Number) defaultValue).doubleValue();
				break;
		}
	}

	@Override
	public String toString() {
		return "Setting{" +
				"name='" + name + '\'' +
				", tag='" + tag + '\'' +
				", type='" + type + '\'' +
				", value=" + getValueAsString() +
				'}';
	}

	private String getValueAsString() {
		switch (type) {
			case "boolean": return Boolean.toString(boolValue);
			case "combobox": return currentValue;
			case "label": return stringValue;
			case "double":
			case "int": return Double.toString(sliderValue);
			default: return "";
		}
	}

}
