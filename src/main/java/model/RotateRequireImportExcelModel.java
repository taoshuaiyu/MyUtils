package model;

import util.excel.Excel;
import util.excel.ImportModel;
import util.excel.RegexType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 二级基地轮转要求导入model
 * @author tsy
 * @date 2017年5月22日 下午19:10:06
 */
public class RotateRequireImportExcelModel extends ImportModel {

    @Excel(value = "专业基地名称", nullable = true, isSpecial = true)
    private String baseName;
    @Excel(value = "轮转科室名称", nullable = false, isExistMore = true)
    private String orgAlias;
    @Excel(value = "轮转类别", nullable = false, regexType = RegexType.SPECIALCHAR)
    private String rotateType;
    @Excel(value = "3年年限轮转周期", nullable = true)
    private Integer threeYearPeriod;
    @Excel(value = "2年年限轮转周期", nullable = true)
    private Integer twoYearPeriod;
    @Excel(value = "1年年限轮转周期", nullable = true)
    private Integer oneYearPeriod;

    private List<Exception> list = new ArrayList<Exception>();

    public String getOrgAlias() {
        return orgAlias;
    }

    public void setOrgAlias(String orgAlias) {
        this.orgAlias = orgAlias;
    }

    public String getRotateType() {
        return rotateType;
    }

    public void setRotateType(String rotateType) {
        this.rotateType = rotateType;
    }

    public Integer getThreeYearPeriod() {
        return threeYearPeriod;
    }

    public void setThreeYearPeriod(Integer threeYearPeriod) {
        this.threeYearPeriod = threeYearPeriod;
    }

    public Integer getTwoYearPeriod() {
        return twoYearPeriod;
    }

    public void setTwoYearPeriod(Integer twoYearPeriod) {
        this.twoYearPeriod = twoYearPeriod;
    }

    public Integer getOneYearPeriod() {
        return oneYearPeriod;
    }

    public void setOneYearPeriod(Integer oneYearPeriod) {
        this.oneYearPeriod = oneYearPeriod;
    }

    public List<Exception> getList() {
        return list;
    }

    public void setList(List<Exception> list) {
        this.list = list;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
}
