package com.example.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DayParting {

    //周一
    private boolean monday;

    //周二
    private boolean tuesday;

    //周三
    private boolean wednesday;

    //周四
    private boolean thursday;

    //周五
    private boolean friday;

    //周六
    private boolean saturday;

    //周日
    private boolean sunday;

    //开始时间
    private Integer fromHours;

    //结束时间
    private Integer toHours;

}
