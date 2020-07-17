package com.example.tiktok.message;

import com.example.tiktok.R;

import java.util.ArrayList;
import java.util.List;

public class functionSet {

    public static List<function> getData() {
        List<function> result = new ArrayList();
        result.add(new function(R.drawable.ic_baseline_group_24, "粉丝"));
        result.add(new function(R.drawable.ic_baseline_thumb_up_24, "赞"));
        result.add(new function(R.drawable.ic_baseline_alternate_email_24, "@我的"));
        result.add(new function(R.drawable.ic_baseline_comment_24, "评论"));
        return result;
    }
}