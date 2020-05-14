package com.perficient.service.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthCheck
{
    private Object status;
    private String platform;
    private String region;
    private String zone;
    private Details details;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Details{
    private Long total;
    private Long free;
    private Long threshold;
    Db db;
    DiskSpace diskSpace;
    private Integer hello;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Db{
    private String status;
    Details details;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class DiskSpace
{
    private String status;
    Details details;
}
