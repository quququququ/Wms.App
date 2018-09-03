package com.wms.newwmsapp.tool;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class JSONHelper {
	public static String toJSON(Object obj) {
		JSONStringer js = new JSONStringer();
		serialize(js, obj);
		return js.toString();
	}

	public static JSONStringer serialize(JSONStringer js, Object o) {
		if (isNull(o)) {
			try {
				js.value(null);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return js;
		}

		Class<?> clazz = o.getClass();
		if (isObject(clazz)) {
			serializeObject(js, o);
		} else if (isArray(clazz)) {
			serializeArray(js, o);
		} else if (isCollection(clazz)) {
			Collection<?> collection = (Collection<?>) o;
			serializeCollect(js, collection);
		} else {
			try {
				js.value(o);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return js;
	}

	private static void serializeArray(JSONStringer js, Object array) {
		try {
			js.array();
			for (int i = 0; i < Array.getLength(array); ++i) {
				Object o = Array.get(array, i);
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void serializeCollect(JSONStringer js,
			Collection<?> collection) {
		try {
			js.array();
			for (Object o : collection) {
				serialize(js, o);
			}
			js.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void serializeObject(JSONStringer js, Object obj) {
		try {
			js.object();
			for (Field f : obj.getClass().getFields()) {
				Object o = f.get(obj);
				js.key(f.getName());
				serialize(js, o);
			}
			js.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> T parseObject(JSONObject jo, Class<T> clazz) {
		if (clazz == null || isNull(jo)) {
			return null;
		}

		T obj = createInstance(clazz);
		if (obj == null) {
			return null;
		}

		for (Field f : clazz.getFields()) {
			setField(obj, f, jo);
		}

		return obj;
	}

	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}
		JSONObject jo = null;
		try {
			jo = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseObject(jo, clazz);
	}

	public static <T> T[] parseArray(JSONArray ja, Class<T> clazz) {
		if (clazz == null || isNull(ja)) {
			return null;
		}

		int len = ja.length();

		@SuppressWarnings("unchecked")
		T[] array = (T[]) Array.newInstance(clazz, len);

		for (int i = 0; i < len; ++i) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				T o = parseObject(jo, clazz);
				array[i] = o;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return array;
	}

	public static <T> T[] parseArray(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}
		JSONArray jo = null;
		try {
			jo = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseArray(jo, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> parseCollection(JSONArray ja,
			Class<?> collectionClazz, Class<T> genericType) {

		if (collectionClazz == null || genericType == null || isNull(ja)) {
			return null;
		}

		Collection<T> collection = (Collection<T>) createInstance(collectionClazz);

		for (int i = 0; i < ja.length(); ++i) {
			try {
				JSONObject jo = ja.getJSONObject(i);
				T o = parseObject(jo, genericType);
				collection.add(o);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return collection;
	}

	public static <T> Collection<T> parseCollection(String jsonString,
			Class<?> collectionClazz, Class<T> genericType) {
		if (collectionClazz == null || genericType == null
				|| jsonString == null || jsonString.length() == 0) {
			return null;
		}
		JSONArray jo = null;
		try {
			jo = new JSONArray(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (isNull(jo)) {
			return null;
		}

		return parseCollection(jo, collectionClazz, genericType);
	}

	private static <T> T createInstance(Class<T> clazz) {
		if (clazz == null)
			return null;
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private static void setField(Object obj, Field f, JSONObject jo) {
		String name = f.getName();
		Class<?> clazz = f.getType();
		try {
			if (isArray(clazz)) {
				Class<?> c = clazz.getComponentType();
				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object array = parseArray(ja, c);
					f.set(obj, array);
				}
			} else if (isCollection(clazz)) {
				Class<?> c = null;
				Type gType = f.getGenericType();
				if (gType instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType) gType;
					Type[] targs = ptype.getActualTypeArguments();
					if (targs != null && targs.length > 0) {
						Type t = targs[0];
						c = (Class<?>) t;
					}
				}

				JSONArray ja = jo.optJSONArray(name);
				if (!isNull(ja)) {
					Object o = parseCollection(ja, clazz, c);
					f.set(obj, o);
				}
			} else if (isSingle(clazz)) {
				Object o = jo.opt(name);
				if (o != null) {
					f.set(obj, o);
				}
			} else if (isObject(clazz)) {
				JSONObject j = jo.optJSONObject(name);
				if (!isNull(j)) {
					Object o = parseObject(j, clazz);
					f.set(obj, o);
				}
			} else {
				throw new Exception("unknow type!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isNull(Object obj) {
		if (obj instanceof JSONObject) {
			return JSONObject.NULL.equals(obj);
		}
		return obj == null;
	}

	private static boolean isSingle(Class<?> clazz) {
		return isBoolean(clazz) || isNumber(clazz) || isString(clazz);
	}

	public static boolean isBoolean(Class<?> clazz) {
		return (clazz != null)
				&& ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class
						.isAssignableFrom(clazz)));
	}

	public static boolean isNumber(Class<?> clazz) {
		return (clazz != null)
				&& ((Byte.TYPE.isAssignableFrom(clazz))
						|| (Short.TYPE.isAssignableFrom(clazz))
						|| (Integer.TYPE.isAssignableFrom(clazz))
						|| (Long.TYPE.isAssignableFrom(clazz))
						|| (Float.TYPE.isAssignableFrom(clazz))
						|| (Double.TYPE.isAssignableFrom(clazz)) || (Number.class
							.isAssignableFrom(clazz)));
	}

	public static boolean isString(Class<?> clazz) {
		return (clazz != null)
				&& ((String.class.isAssignableFrom(clazz))
						|| (Character.TYPE.isAssignableFrom(clazz)) || (Character.class
							.isAssignableFrom(clazz)));
	}

	private static boolean isObject(Class<?> clazz) {
		return clazz != null && !isSingle(clazz) && !isArray(clazz)
				&& !isCollection(clazz);
	}

	public static boolean isArray(Class<?> clazz) {
		return clazz != null && clazz.isArray();
	}

	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}
}
