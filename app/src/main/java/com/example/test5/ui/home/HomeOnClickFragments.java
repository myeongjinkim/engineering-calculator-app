package com.example.test5.ui.home;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.app.Fragment;
import android.view.View;


public class HomeOnClickFragments {
    public static class FragmentHolder {
        HomeFragment fragment;
        public FragmentHolder(HomeFragment fragment) {
            this.fragment = fragment;
        }
    }
    public static HomeFragment getTagFragment(View view) {
        for (View v = view; v != null; v = (v.getParent() instanceof View) ? (View)v.getParent() : null) {
            Object tag = v.getTag();
            if (tag != null && tag instanceof FragmentHolder) {
                return ((FragmentHolder)tag).fragment;
            }
        }
        return null;
    }
    public static String getCallingMethodName(int callsAbove) {
        Exception e = new Exception();
        e.fillInStackTrace();
        String methodName = e.getStackTrace()[callsAbove+1].getMethodName();
        return methodName;
    }
    public static void invokeFragmentButtonHandler(View v, int callsAbove) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        String methodName = getCallingMethodName(callsAbove+1);
        HomeFragment f = HomeOnClickFragments.getTagFragment(v);
        Method m = f.getClass().getMethod(methodName, new Class[] { View.class });
        m.invoke(f, v);
    }
    public static void invokeFragmentButtonHandler(View v) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        invokeFragmentButtonHandler(v,1);
    }
    public static void invokeFragmentButtonHandlerNoExc(View v) {
        try {
            invokeFragmentButtonHandler(v,1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void registerTagFragment(View rootView, HomeFragment fragment) {
        rootView.setTag(new FragmentHolder(fragment));
    }
}
