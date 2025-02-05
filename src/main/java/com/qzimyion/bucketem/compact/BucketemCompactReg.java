package com.qzimyion.bucketem.compact;

import com.qzimyion.bucketem.compact.WW.WWBucketEmClient;
import com.qzimyion.bucketem.compact.WW.WWBucketEmEvents;
import com.qzimyion.bucketem.compact.WW.WWBucketEmItems;
import com.qzimyion.bucketem.compact.spelunkery.SpelunkeryBucketemClient;
import com.qzimyion.bucketem.compact.spelunkery.SpelunkeryBucketemItems;

public class BucketemCompactReg {

    public static void initializeCompacts(){
        //Spelunkery
        //if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemItems.registerItems();

        //WW
        if (IsModLoaded.isWWModLoaded()) WWBucketEmItems.regWWBucketEmItems();
        if (IsModLoaded.isWWModLoaded()) WWBucketEmEvents.registerWWBucketEMEvents();
    }

    public static void initializeClientCompacts(){
        //Spelunkery
        //if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemClient.SItemPredicateModels();

        //WW
        if (IsModLoaded.isWWModLoaded()) WWBucketEmClient.WWBucketemItemPredicateModels();
    }

}
