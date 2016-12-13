package com.wangxuhui.navigationdrawer;

class Congress {
    String congress;
}
class Urls {
    String pdf;
}
class Last_version {
    String version_name;
    Urls urls;
}
class History {
    boolean active;
}
public class Data_bill {
    String bill_id;
    String short_title;
    String introduced_on;
    String bill_type;
    String official_title;
    Sponsor sponsor;
    String chamber;
    History history;
    Congress urls;
    Last_version last_version;
}
