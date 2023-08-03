package de.app.fivegla.integration.agranimo.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Zone {

    private boolean status;
    private ZoneData data;
    private String irrigationType;
    private String name;
    private String code;
    private String variety;
    @SerializedName("soiltype")
    private String soilType;
    @SerializedName("field_id")
    private String fieldId;
    @SerializedName("company_id")
    private String companyId;
    private Date createdAt;
    private Date updatedAt;
    private String id;
}
