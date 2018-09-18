package com.falcon.springboot.falcondemo.falcondemo.util;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.test.context.junit4.SpringRunner;

import com.falcon.springboot.falcondemo.util.JSONValidate;

@RunWith(SpringRunner.class)
public class JSONValidateTest {
	@Test
	public void validateTestInValidJson() {
		Assert.assertFalse(JSONValidate.isValid("asdsadasd"));
	}

	@Test
	public void validateTestValidJson() {
		Assert.assertTrue(JSONValidate.isValid("{\"content\":\"test contetn\"}"));
	}
}
