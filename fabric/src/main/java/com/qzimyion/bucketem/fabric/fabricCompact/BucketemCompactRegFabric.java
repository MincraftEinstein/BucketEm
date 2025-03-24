package com.qzimyion.bucketem.fabric.fabricCompact;

import com.qzimyion.bucketem.compact.IsModLoadedForCrossContent;
import com.qzimyion.bucketem.fabric.fabricCompact.WW.WWBucketEmModelPredicates;
import com.qzimyion.bucketem.platform.fabric.ClientHelperImpl;

public class BucketemCompactRegFabric {

    public static void initClientContent(){
        //==Wilder wild==//
        if (IsModLoadedForCrossContent.isWWLoaded()){
            //ClientHelperImpl.addModelPredicatesRegistration(WWBucketEmModelPredicates::register);
        }
    }
}
