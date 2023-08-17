package de.app.fivegla.integration.sentek.model.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Reading {
    @CsvBindByName(column = "Date Time")
    @CsvDate(value = "yyyy/MM/dd HH:mm:ss")
    private Date dateTime;
    @CsvBindByName(column = "V1")
    private Double v1;
    @CsvBindByName(column = "V2")
    private Double v2;
    @CsvBindByName(column = "A1(5)")
    private Double a1;
    @CsvBindByName(column = "T1(5)")
    private Double t1;
    @CsvBindByName(column = "A2(15)")
    private Double a2;
    @CsvBindByName(column = "T2(15)")
    private Double t2;
    @CsvBindByName(column = "A3(25)")
    private Double a3;
    @CsvBindByName(column = "T3(25)")
    private Double t3;
    @CsvBindByName(column = "A4(35)")
    private Double a4;
    @CsvBindByName(column = "T4(35)")
    private Double t4;
    @CsvBindByName(column = "A5(45)")
    private Double a5;
    @CsvBindByName(column = "T5(45)")
    private Double t5;
    @CsvBindByName(column = "A6(55)")
    private Double a6;
    @CsvBindByName(column = "T6(55)")
    private Double t6;
    @CsvBindByName(column = "A7(65)")
    private Double a7;
    @CsvBindByName(column = "T7(65)")
    private Double t7;
    @CsvBindByName(column = "A8(75)")
    private Double a8;
    @CsvBindByName(column = "T8(75)")
    private Double t8;
    @CsvBindByName(column = "A9(85)")
    private Double a9;
    @CsvBindByName(column = "T9(85)")
    private Double t9;
}
