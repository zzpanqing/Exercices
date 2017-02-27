package com.qing.imagetests.imagetests;

import org.apache.maven.artifact.ant.shaded.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by qing on 27/02/17.
 */

@RunWith(RobolectricTestRunner.class)
public class RobolectricTest {
    @Before
    public  void setUp(){
        // 初始化 比如：网络请求 构造 activity
    }

    @Before
    public void prepareHttpResponse(String filePath) throws IOException{
        String netData = FileUtils.readFileToString(
                FileUtils.toFile(
                    getClass().getResource(filePath)),
                HTTP.UTF_8
        );
        Robolectric.setDefaultHttpResponse(200, netData);
    }

    @Test
    public void testCase(){
        //执行逻辑判断
    }
}
