package org.toughsocks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toughsocks.common.PageResult;
import org.toughsocks.component.Memarylogger;
import org.toughsocks.component.TicketCache;
import org.toughsocks.entity.SocksSession;


import java.util.ArrayList;

@RestController
public class TicketController {

    @Autowired
    private TicketCache ticketCache;

    @Autowired
    private Memarylogger logger;

    @GetMapping({"/api/ticket/query","/admin/ticket/query"})
    public PageResult<SocksSession> queryTicket(@RequestParam(defaultValue = "0") int start,
                                                @RequestParam(defaultValue = "40") int count,
                                                String username,String srcAddr,Integer srcPort,
                                                String dstAddr,Integer dstPort, String startTime, String endtime){

        try {
            return ticketCache.queryTicket(start,count,username,srcAddr,srcPort,dstAddr,dstPort,startTime,endtime);
        } catch (Exception e) {
            logger.error(String.format("/admin 查询上网日志发生错误, %s", e.getMessage()),e, Memarylogger.SYSTEM);
            return new PageResult<>(start,0, new ArrayList<>());
        }
    }
}
