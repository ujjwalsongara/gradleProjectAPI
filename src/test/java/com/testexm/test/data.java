package com.testexm.test;

import com.testexm.BaseClass;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class data extends BaseClass {

        @DataProvider (name = "dataProviders")

        public Object[][] dataProviders()
        {
            return new Object[][] {{"First-Value"}, {"Second-Value"}};
    }

        @Test (dataProvider = "dataProviders")

        public void test (String val)
      {
        System.out.println("Passed Parameter Is : " + val);
    }

}

