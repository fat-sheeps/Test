package com.example.utils.uniad;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReportQueryRequest extends CommonParams {
    private String start_date;
    private String end_date;
    private String extend;
    private Integer page;
    private Integer page_size;
}
