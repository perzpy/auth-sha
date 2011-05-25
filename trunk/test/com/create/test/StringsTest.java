package com.create.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import com.create.util.Strings;

/**
 * 测试Strings类
 *
 * @author perzer,cxb
 * @date Feb 23, 2011
 */
public class StringsTest extends TestCase {

	@Test
	public void testAddBeginEnd() {
		Assert.assertEquals(null, Strings.addBeginEnd(null, null));
		Assert.assertEquals("%value%", Strings.addBeginEnd("value", "%"));
		Assert.assertEquals("%value@", Strings.addBeginEnd("value", new String[]{"%", "@"}));
		Assert.assertEquals("%value@", Strings.addBeginEnd("value", new String[]{"%", "@", "^"}));
	}
}
