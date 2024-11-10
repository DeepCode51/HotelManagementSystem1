package cn.mafangui.hotel.service;

import cn.mafangui.hotel.response.AjaxResult;

public interface CheckInService {

    AjaxResult checkIn(int orderId);

    AjaxResult checkOut(int orderId);

}
