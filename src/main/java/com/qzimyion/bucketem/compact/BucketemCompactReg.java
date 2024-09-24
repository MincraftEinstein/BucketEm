package com.qzimyion.bucketem.compact;

import com.qzimyion.bucketem.compact.spelunkery.SpelunkeryBucketemClient;
import com.qzimyion.bucketem.compact.spelunkery.SpelunkeryBucketemItems;

public class BucketemCompactReg {

    public static void initializeCompacts(){
        //Spelunkery
        //if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemItems.registerItems();
    }

    public static void initializeClientCompacts(){
        //Spelunkery
        if (IsModLoaded.isSpelunkeryModLoaded()) SpelunkeryBucketemClient.SItemPredicateModels();
    }

}
