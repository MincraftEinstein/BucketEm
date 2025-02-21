package com.qzimyion.bucketem.compact;

import com.qzimyion.bucketem.compact.WW.*;

public class BucketemCompactReg {

    public static void initializeCompacts(){
        //Spelunkery
        //if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemItems.registerItems();
    }

    public static void initializeClientCompacts(){
        //Spelunkery
        //if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemClient.SItemPredicateModels();

        //Wilder wilds
        if (IsModLoaded.isWWModLoaded()) WWBucketEmModelPredicates.register();
    }

}
