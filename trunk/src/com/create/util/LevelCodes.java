package com.create.util;

/**
 * 层级编码工具类
 *
 * @author perzer
 * @date Mar 10, 2011
 */
public class LevelCodes {
	
	/**
	 * 得到下级第一个级编码
	 * <p>
	 * 例:<br>
	 * 1.getFirstChildLevelCode("") "0000000001"<br>
	 * 2.getFirstChildLevelCode("00000000010000000002")
	 * "000000000100000000020000000001"<br>
	 * 3.getFirstChildLevelCode(null) ""<br>
	 * 
	 * @param levelCode 级编码
	 * @return 下级编码
	 */
	public static String getFirstChildLevelCode(String levelCode) {
		if (levelCode == null)
			return "";
		return levelCode + Strings.getRepeatStrs("0", Constants.LEVELCODE_LEN - 1) + "1";
	}
	
	/**
	 * 得到增量5的LevelCode
	 * <p>
	 * 例:<br>
	 * 1.getLagerLevelCode("0000000001") "0000000002"<br>
	 * 2.getLagerLevelCode("00000000010000000002") "00000000010000000003"<br>
	 * 
	 * @param levelCode
	 * @return 级编码
	 */
	public static String getLagerLevelCode(String levelCode) {
		if (!Strings.isBlank(levelCode) && levelCode.length() >= Constants.LEVELCODE_LEN && levelCode.length() % Constants.LEVELCODE_LEN == 0) {
			String parentLevelCode = levelCode.substring(0, levelCode.length() - Constants.LEVELCODE_LEN);
			long childIndex = Long.parseLong(levelCode.replaceFirst(parentLevelCode, "").replaceAll("(0*)(\\d*)", "$2"));
			childIndex += 5;
			if (String.valueOf(childIndex).length() > Constants.LEVELCODE_LEN) {
				return "";
			} else {
				return parentLevelCode + Strings.getRepeatStrs("0", (Constants.LEVELCODE_LEN - String.valueOf(childIndex).length())) + childIndex;
			}
		}
		return "";
	}

}
