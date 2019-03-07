package com.aplus.jx.identity.reader.model;

import com.sun.jna.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wang zhengtao
 */
public class IdCardTxtInfo extends Structure {
    public byte[] name = new byte[31];
    public byte[] Sex = new byte[3];
    public byte[] nation = new byte[10];
    public byte[] borndate = new byte[9];
    public byte[] address = new byte[71];
    public byte[] idno = new byte[19];
    public byte[] department = new byte[31];
    public byte[] StartDate = new byte[9];
    public byte[] EndDate = new byte[9];
    public byte[] Reserve = new byte[37];
    public byte[] AppAddress1 = new byte[71];

    @Override
    protected List<String> getFieldOrder() {
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("Sex");
        list.add("nation");
        list.add("borndate");
        list.add("address");
        list.add("idno");
        list.add("department");
        list.add("StartDate");
        list.add("EndDate");
        list.add("Reserve");
        list.add("AppAddress1");
        return list;
    }
}