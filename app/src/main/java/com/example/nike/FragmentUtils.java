package com.example.nike;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void addFragment(FragmentManager fm, Fragment fragment,int containerID)
    {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerID,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
