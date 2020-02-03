package com.crui.house.common.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * house
 * 
 * @author crui
 * @version 1.0.0 2020-01-10
 */
@Data
public class House implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3075999629811656292L;

    /** 主键id */
    private Long id;

    /** 房产名称 */
    private String name;

    /** type */
//    @JsonProperty("typeStr")
    private Integer type;

    /** price */
    private Integer price;

    /** images */
    private String images;

    /** area */
    private Integer area;

    /** beds */
    private Integer beds;

    /** baths */
    private Integer baths;

    /** rating */
    private Double rating;

    /** remarks */
    private String remarks;

    /** properties */
    private String properties;

    /** floorPlan */
    private String floorPlan;

    /** tags */
    private String tags;

    /** createTime */
    private Date createTime;

    /** cityId */
    private Integer cityId;

    /** communityId */
    private Integer communityId;

    /** address */
    private String address;

    /** 1-上架，2-下架 */
    private Integer state;

    private String sort = "time_desc";

    private String firstImg;
    private List<String> imagesList = Lists.newArrayList();
    private List<String> floorPlanList = Lists.newArrayList();
    private List<MultipartFile> houseFiles;// = Lists.newArrayList();
    private List<MultipartFile> floorPlanFiles;// = Lists.newArrayList();

    private Long userId;
    private boolean bookmarked;

    private List<Long> ids;

    private String typeStr;

    public void setType(Integer type) {
        this.type = type;
        if (Objects.equal(type, 1) && Objects.equal(type, 0)) {
            this.typeStr = "For Sale";
        }else {
            this.typeStr = "For Rent";
        }
    }

    public void setImages(String images) {
        this.images = images;
        if (!Strings.isNullOrEmpty(images)){
            List<String> list = (List<String>) Splitter.on(",").splitToList(images);
            if (!list.isEmpty()){
                this.firstImg = list.get(0);
                this.imagesList = list;
            }
        }
    }
}