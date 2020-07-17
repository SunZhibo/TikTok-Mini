package com.example.tiktok.message;
import com.example.tiktok.R;

import java.util.ArrayList;
import java.util.List;

public class MessegeSet {

    public static List<Messeges> getData() {
        List<Messeges> result = new ArrayList();
        result.add(new Messeges(R.drawable.p1, "苹果", "明天几点走？","刚刚"));
        result.add(new Messeges(R.drawable.p2, "梨", "[Hi]","15:07"));
        result.add(new Messeges(R.drawable.p3, "葡萄", "转发[视频]","14:55"));
        result.add(new Messeges(R.drawable.p4, "White", "冲冲冲","14:33"));
        result.add(new Messeges(R.drawable.p5, "Black", "我也沉了hhh","14:03"));
        result.add(new Messeges(R.drawable.p6, "土豆", "[捂脸哭][捂脸哭]","9:16"));
        result.add(new Messeges(R.drawable.p7, "杨桃", "五角星？","7:15"));
        result.add(new Messeges(R.drawable.p5, "Black", "我也沉了hhh","14:03"));
        result.add(new Messeges(R.drawable.p6, "土豆", "[捂脸哭][捂脸哭]","9:16"));
        result.add(new Messeges(R.drawable.p7, "杨桃", "五角星？","7:15"));
        return result;
    }
}
