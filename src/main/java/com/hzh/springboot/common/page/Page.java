package com.hzh.springboot.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private long page=1;
    private long pageSize=50;

    private long sPage=0;

    public static Page getData(long page,long pageSize){
        return  new Page(page,pageSize,(page-1)*pageSize);
    };

    public void incre(){
        this.page++;
        this.setsPage();
    }

    public void setsPage() {
        this.sPage = (page-1)*pageSize;
    }
}
