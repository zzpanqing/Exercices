package com.qing.unittest;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.view.menu.ShowableListMenu;

import org.apache.maven.artifact.ant.shaded.FileUtils;
import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowPath;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.util.List;

import static java.net.Proxy.Type.HTTP;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        // 用file 的数据模拟网络请求数据
        Robolectric.setDefaultHttpResponse(200, netData);
    }

    @Test
    public void testCase(){
        //执行逻辑判断
    }

    // 测试 activity 的生成
    @Test
    public void testSampleActivity() {
        MainActivity mainActivity = (MainActivity) Robolectric.buildActivity(MainActivity.class)
                .create()  // 对用 Activity.onCreate()
                .resume()  // 对应 Activity.onResume()
                .get();
        assertNotNull(mainActivity);
    }

    // 测试 activity 的切换
    @Test
    public void testActivityTurn(MainActivity firstActivity
                                ,Class secondActivity){
        Intent intent = new Intent(firstActivity.getApplicationContext(),
                secondActivity);
        assertEquals(intent, Robolectric.shadowOf(firstActivity)
        .getNextStartedActivity());
    }

    // 测试 dialog 是否弹出
    // ShadowDialog 代替 android 的 Dialog
    @Test
    public void testDialog(){
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
    }

    // 测试 toast 是否弹出
    // shadowToast 代替 android 的 Toast
    public void testToast(String toastContent){
        ShadowHandler.idleMainLooper();
        assertNotNull(toastContent, ShadowToast.getTextOfLatestToast());
    }

    // 声明一个用来代替 Point 的 Shadow 对象
    @Implements(Point.class)
    public class ShadowPoint {
        @RealObject // @RealObjiect 获取一个 android 对象
        private Point realPoint;

        public void _constructor_(int x, int y) { // shadow 的构造函数
            realPoint.x = x;
            realPoint.y = y;
        }
    }
    //需要这样声明自定义 shadow 类才有效
    @Config(shadows = RobolectricTest.ShadowPoint.class)

    //mock
    @Test
    void testWithMock(){
        List list = mock(List.class);

        When(sample.dosomething()).thenReturn(someAction);

        //验证函数执行次数
        list.add(1);
        verify(list).add(1); //验证函数执行
        verify(list, time(3)).add(1); //验证函数执行次数

    }
}
